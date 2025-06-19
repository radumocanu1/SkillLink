package unibuc.SkillLink.handlers.ads;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unibuc.SkillLink.DTOs.ads.AdResponse;
import unibuc.SkillLink.commands.ads.GetAdCommand;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.mappers.ads.AdsMapper;
import unibuc.SkillLink.models.Ad;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.AdsRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAdCommandHandlerTest {

    @Mock
    private AdsMapper adsMapper;

    @Mock
    private AdsRepository adsRepository;

    @InjectMocks
    private GetAdCommandHandler handler;

    @Test
    void shouldGetAdById() {
        // Arrange
        UUID adId = UUID.randomUUID();
        String providerUsername = "testprovider";
        String providerPicture = "profile.jpg";

        Provider provider = new Provider();
        provider.setUsername(providerUsername);
        provider.setProfilePicture(providerPicture);

        Ad ad = new Ad();
        ad.setId(adId);
        ad.setTitle("Test Ad");
        ad.setDescription("Test Description");
        ad.setRate(100);
        ad.setProvider(provider);

        AdResponse expectedResponse = new AdResponse(
            ad.getTitle(),
            providerPicture,
            providerUsername,
            ad.getRate(),
            adId,
            ad.getDescription(),
            null
        );

        when(adsRepository.findById(adId)).thenReturn(Optional.of(ad));
        when(adsMapper.fromEntity(ad)).thenReturn(expectedResponse);

        // Act
        AdResponse result = handler.handle(new GetAdCommand(adId));

        // Assert
        assertNotNull(result);
        assertEquals(ad.getTitle(), result.getTitle());
        assertEquals(ad.getDescription(), result.getDescription());
        assertEquals(ad.getRate(), result.getRate());
        assertEquals(providerUsername, result.getProviderUsername());
        assertEquals(providerPicture, result.getProviderPicture());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenAdDoesNotExist() {
        // Arrange
        UUID adId = UUID.randomUUID();
        when(adsRepository.findById(adId)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> handler.handle(new GetAdCommand(adId))
        );

        assertEquals("Ad", exception.getEntityName());
        assertEquals(adId.toString(), exception.getId());
    }
} 