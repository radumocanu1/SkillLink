package unibuc.SkillLink.handlers.ads;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unibuc.SkillLink.commands.ads.DeleteAdCommand;
import unibuc.SkillLink.exceptions.ForbiddenException;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.models.Ad;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.AdsRepository;
import unibuc.SkillLink.services.AdPictureService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteAdCommandHandlerTest {

    @Mock
    private AdsRepository adsRepository;

    @Mock
    private AdPictureService adPictureService;

    @InjectMocks
    private DeleteAdCommandHandler handler;

    @Test
    void shouldDeleteAdWithPicture() {
        // Arrange
        UUID adId = UUID.randomUUID();
        String providerUsername = "testprovider";
        String picturePath = "ad-picture.jpg";

        Provider provider = new Provider();
        provider.setUsername(providerUsername);

        Ad ad = new Ad();
        ad.setId(adId);
        ad.setProvider(provider);
        ad.setPicture(picturePath);

        when(adsRepository.findById(adId)).thenReturn(Optional.of(ad));

        // Act
        handler.handle(new DeleteAdCommand(adId, providerUsername));

        // Assert
        verify(adPictureService).deleteAdPicture(picturePath);
        verify(adsRepository).delete(ad);
    }

    @Test
    void shouldDeleteAdWithoutPicture() {
        // Arrange
        UUID adId = UUID.randomUUID();
        String providerUsername = "testprovider";

        Provider provider = new Provider();
        provider.setUsername(providerUsername);

        Ad ad = new Ad();
        ad.setId(adId);
        ad.setProvider(provider);
        ad.setPicture(null);

        when(adsRepository.findById(adId)).thenReturn(Optional.of(ad));

        // Act
        handler.handle(new DeleteAdCommand(adId, providerUsername));

        // Assert
        verify(adPictureService, never()).deleteAdPicture(any());
        verify(adsRepository).delete(ad);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenAdDoesNotExist() {
        // Arrange
        UUID adId = UUID.randomUUID();
        String providerUsername = "testprovider";

        when(adsRepository.findById(adId)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> handler.handle(new DeleteAdCommand(adId, providerUsername))
        );

        assertEquals("Ad", exception.getEntityName());
        assertEquals(adId.toString(), exception.getId());
        verify(adsRepository, never()).delete(any());
    }

    @Test
    void shouldThrowForbiddenExceptionWhenProviderDoesNotOwnAd() {
        // Arrange
        UUID adId = UUID.randomUUID();
        String ownerUsername = "owner";
        String otherUsername = "other";

        Provider provider = new Provider();
        provider.setUsername(ownerUsername);

        Ad ad = new Ad();
        ad.setId(adId);
        ad.setProvider(provider);

        when(adsRepository.findById(adId)).thenReturn(Optional.of(ad));

        // Act & Assert
        assertThrows(
            ForbiddenException.class,
            () -> handler.handle(new DeleteAdCommand(adId, otherUsername))
        );

        verify(adsRepository, never()).delete(any());
    }
} 