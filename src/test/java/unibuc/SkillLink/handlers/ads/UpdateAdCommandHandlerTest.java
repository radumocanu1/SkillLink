package unibuc.SkillLink.handlers.ads;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unibuc.SkillLink.DTOs.ads.AdResponse;
import unibuc.SkillLink.DTOs.ads.CreateAdRequest;
import unibuc.SkillLink.commands.ads.UpdateAdCommand;
import unibuc.SkillLink.exceptions.ForbiddenException;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.mappers.ads.AdsMapper;
import unibuc.SkillLink.models.Ad;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.AdsRepository;
import unibuc.SkillLink.services.AdPictureService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateAdCommandHandlerTest {

    @Mock
    private AdsRepository adsRepository;

    @Mock
    private AdPictureService adPictureService;

    @Mock
    private AdsMapper adsMapper;

    @InjectMocks
    private UpdateAdCommandHandler handler;

    @Test
    void shouldUpdateAdWithoutPicture() {
        // Arrange
        UUID adId = UUID.randomUUID();
        String providerUsername = "testprovider";

        CreateAdRequest request = new CreateAdRequest();
        request.setTitle("Updated Title");
        request.setDescription("Updated Description that is long enough to pass validation");
        request.setRate(150);
        request.setPicture(null);

        Provider provider = new Provider();
        provider.setUsername(providerUsername);

        Ad existingAd = new Ad();
        existingAd.setId(adId);
        existingAd.setProvider(provider);
        existingAd.setTitle("Old Title");
        existingAd.setDescription("Old Description");
        existingAd.setRate(100);

        Ad updatedAd = new Ad();
        updatedAd.setId(adId);
        updatedAd.setProvider(provider);
        updatedAd.setTitle(request.getTitle());
        updatedAd.setDescription(request.getDescription());
        updatedAd.setRate(request.getRate());

        AdResponse expectedResponse = new AdResponse(
            request.getTitle(),
            null,
            providerUsername,
            request.getRate(),
            adId,
            request.getDescription(),
            null
        );

        when(adsRepository.findById(adId)).thenReturn(Optional.of(existingAd));
        when(adsRepository.save(existingAd)).thenReturn(updatedAd);
        when(adsMapper.fromEntity(updatedAd)).thenReturn(expectedResponse);

        // Act
        AdResponse result = handler.handle(new UpdateAdCommand(adId, providerUsername, request));

        // Assert
        assertNotNull(result);
        assertEquals(request.getTitle(), result.getTitle());
        assertEquals(request.getDescription(), result.getDescription());
        assertEquals(request.getRate(), result.getRate());
        assertEquals(providerUsername, result.getProviderUsername());
        assertNull(result.getPicture());
        verify(adPictureService, never()).deleteAdPicture(any());
        verify(adPictureService, never()).saveAdPicture(any(), any());
    }

    @Test
    void shouldUpdateAdWithNewPicture() {
        // Arrange
        UUID adId = UUID.randomUUID();
        String providerUsername = "testprovider";
        String oldPicturePath = "old-picture.jpg";
        String newPicturePath = "new-picture.jpg";

        CreateAdRequest request = new CreateAdRequest();
        request.setTitle("Updated Title");
        request.setDescription("Updated Description that is long enough to pass validation");
        request.setRate(150);
        request.setPicture("base64picture");

        Provider provider = new Provider();
        provider.setUsername(providerUsername);

        Ad existingAd = new Ad();
        existingAd.setId(adId);
        existingAd.setProvider(provider);
        existingAd.setTitle("Old Title");
        existingAd.setDescription("Old Description");
        existingAd.setRate(100);
        existingAd.setPicture(oldPicturePath);

        Ad updatedAd = new Ad();
        updatedAd.setId(adId);
        updatedAd.setProvider(provider);
        updatedAd.setTitle(request.getTitle());
        updatedAd.setDescription(request.getDescription());
        updatedAd.setRate(request.getRate());
        updatedAd.setPicture(newPicturePath);

        AdResponse expectedResponse = new AdResponse(
            request.getTitle(),
            null,
            providerUsername,
            request.getRate(),
            adId,
            request.getDescription(),
            newPicturePath
        );

        when(adsRepository.findById(adId)).thenReturn(Optional.of(existingAd));
        when(adPictureService.saveAdPicture(request.getPicture(), adId)).thenReturn(newPicturePath);
        when(adsRepository.save(existingAd)).thenReturn(updatedAd);
        when(adsMapper.fromEntity(updatedAd)).thenReturn(expectedResponse);

        // Act
        AdResponse result = handler.handle(new UpdateAdCommand(adId, providerUsername, request));

        // Assert
        assertNotNull(result);
        assertEquals(request.getTitle(), result.getTitle());
        assertEquals(request.getDescription(), result.getDescription());
        assertEquals(request.getRate(), result.getRate());
        assertEquals(providerUsername, result.getProviderUsername());
        assertEquals(newPicturePath, result.getPicture());
        verify(adPictureService).deleteAdPicture(oldPicturePath);
        verify(adPictureService).saveAdPicture(request.getPicture(), adId);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenAdDoesNotExist() {
        // Arrange
        UUID adId = UUID.randomUUID();
        String providerUsername = "testprovider";
        CreateAdRequest request = new CreateAdRequest();

        when(adsRepository.findById(adId)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> handler.handle(new UpdateAdCommand(adId, providerUsername, request))
        );

        assertEquals("Ad", exception.getEntityName());
        assertEquals(adId.toString(), exception.getId());
    }

    @Test
    void shouldThrowForbiddenExceptionWhenProviderDoesNotOwnAd() {
        // Arrange
        UUID adId = UUID.randomUUID();
        String ownerUsername = "owner";
        String otherUsername = "other";
        CreateAdRequest request = new CreateAdRequest();

        Provider provider = new Provider();
        provider.setUsername(ownerUsername);

        Ad ad = new Ad();
        ad.setId(adId);
        ad.setProvider(provider);

        when(adsRepository.findById(adId)).thenReturn(Optional.of(ad));

        // Act & Assert
        assertThrows(
            ForbiddenException.class,
            () -> handler.handle(new UpdateAdCommand(adId, otherUsername, request))
        );

        verify(adsRepository, never()).save(any());
    }
} 