package unibuc.SkillLink.handlers.ads;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import unibuc.SkillLink.DTOs.ads.AdResponse;
import unibuc.SkillLink.DTOs.ads.CreateAdRequest;
import unibuc.SkillLink.abstractions.Mediator;
import unibuc.SkillLink.commands.ads.CreateAdCommand;
import unibuc.SkillLink.commands.providers.GetProviderCommand;
import unibuc.SkillLink.mappers.ads.AdsMapper;
import unibuc.SkillLink.models.Ad;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.AdsRepository;
import unibuc.SkillLink.services.AdPictureService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAdCommandHandlerTest {

    @Mock
    private AdsMapper adsMapper;

    @Mock
    private AdsRepository adsRepository;

    @Mock
    private AdPictureService adPictureService;

    @Mock
    private Mediator mediator;

    @InjectMocks
    private CreateAdCommandHandler handler;

    @Test
    void shouldCreateAdWithoutPicture() {
        String providerUsername = "testprovider";
        CreateAdRequest request = new CreateAdRequest();
        request.setTitle("Test Ad Title");
        request.setDescription("Test Ad Description that is long enough to pass validation");
        request.setRate(100);
        request.setPicture(null);

        Provider provider = new Provider();
        provider.setId(UUID.randomUUID());
        provider.setUsername(providerUsername);

        Ad ad = new Ad();
        ad.setId(UUID.randomUUID());
        ad.setTitle(request.getTitle());
        ad.setDescription(request.getDescription());
        ad.setRate(request.getRate());
        ad.setProvider(provider);

        AdResponse expectedResponse = new AdResponse(
            request.getTitle(),
            null,
            providerUsername,
            request.getRate(),
            ad.getId(),
            request.getDescription(),
            null
        );

        when(mediator.handle(any(GetProviderCommand.class))).thenReturn(provider);
        when(adsMapper.toEntity(request)).thenReturn(ad);
        when(adsRepository.save(ad)).thenReturn(ad);
        when(adsMapper.fromEntity(ad)).thenReturn(expectedResponse);

        AdResponse result = handler.handle(new CreateAdCommand(request, providerUsername));

        assertNotNull(result);
        assertEquals(request.getTitle(), result.getTitle());
        assertEquals(request.getDescription(), result.getDescription());
        assertEquals(request.getRate(), result.getRate());
        assertEquals(providerUsername, result.getProviderUsername());
        assertNull(result.getPicture());
    }

    @Test
    void shouldCreateAdWithPicture() {
        String providerUsername = "testprovider";
        CreateAdRequest request = new CreateAdRequest();
        request.setTitle("Test Ad Title");
        request.setDescription("Test Ad Description that is long enough to pass validation");
        request.setRate(100);
        request.setPicture("base64picture");

        Provider provider = new Provider();
        provider.setId(UUID.randomUUID());
        provider.setUsername(providerUsername);

        Ad ad = new Ad();
        ad.setId(UUID.randomUUID());
        ad.setTitle(request.getTitle());
        ad.setDescription(request.getDescription());
        ad.setRate(request.getRate());
        ad.setProvider(provider);

        String savedPicturePath = "pictures/ad-" + ad.getId() + ".jpg";
        Ad savedAd = new Ad();
        savedAd.setId(ad.getId());
        savedAd.setTitle(request.getTitle());
        savedAd.setDescription(request.getDescription());
        savedAd.setRate(request.getRate());
        savedAd.setProvider(provider);
        savedAd.setPicture(savedPicturePath);

        AdResponse expectedResponse = new AdResponse(
            request.getTitle(),
            null,
            providerUsername,
            request.getRate(),
            ad.getId(),
            request.getDescription(),
            savedPicturePath
        );

        when(mediator.handle(any(GetProviderCommand.class))).thenReturn(provider);
        when(adsMapper.toEntity(request)).thenReturn(ad);
        when(adsRepository.save(ad)).thenReturn(ad);
        when(adPictureService.saveAdPicture(request.getPicture(), ad.getId())).thenReturn(savedPicturePath);
        when(adsRepository.save(ad)).thenReturn(savedAd);
        when(adsMapper.fromEntity(savedAd)).thenReturn(expectedResponse);

        AdResponse result = handler.handle(new CreateAdCommand(request, providerUsername));

        assertNotNull(result);
        assertEquals(request.getTitle(), result.getTitle());
        assertEquals(request.getDescription(), result.getDescription());
        assertEquals(request.getRate(), result.getRate());
        assertEquals(providerUsername, result.getProviderUsername());
        assertEquals(savedPicturePath, result.getPicture());
    }
} 