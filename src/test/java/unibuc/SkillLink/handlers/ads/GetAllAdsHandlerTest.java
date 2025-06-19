package unibuc.SkillLink.handlers.ads;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import unibuc.SkillLink.DTOs.ads.AdResponse;
import unibuc.SkillLink.DTOs.ads.AdsPage;
import unibuc.SkillLink.commands.ads.GetAdsPageCommand;
import unibuc.SkillLink.mappers.ads.AdsMapper;
import unibuc.SkillLink.models.Ad;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.AdsRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllAdsHandlerTest {

    @Mock
    private AdsMapper adsMapper;

    @Mock
    private AdsRepository adsRepository;

    @InjectMocks
    private GetAllAdsHandler handler;

    @Test
    void shouldGetAllAdsWithPagination() {
        // Arrange
        int page = 0;
        int size = 10;
        long totalElements = 15;
        int totalPages = 2;

        Provider provider = new Provider();
        provider.setUsername("testprovider");

        Ad ad1 = new Ad();
        ad1.setId(UUID.randomUUID());
        ad1.setTitle("Ad 1");
        ad1.setDescription("Description 1");
        ad1.setRate(100);
        ad1.setProvider(provider);

        Ad ad2 = new Ad();
        ad2.setId(UUID.randomUUID());
        ad2.setTitle("Ad 2");
        ad2.setDescription("Description 2");
        ad2.setRate(200);
        ad2.setProvider(provider);

        List<Ad> ads = List.of(ad1, ad2);
        Page<Ad> pageResult = new PageImpl<>(ads, PageRequest.of(page, size), totalElements);

        AdResponse response1 = new AdResponse(
            ad1.getTitle(),
            null,
            provider.getUsername(),
            ad1.getRate(),
            ad1.getId(),
            ad1.getDescription(),
            null
        );

        AdResponse response2 = new AdResponse(
            ad2.getTitle(),
            null,
            provider.getUsername(),
            ad2.getRate(),
            ad2.getId(),
            ad2.getDescription(),
            null
        );

        when(adsRepository.findAll(any(PageRequest.class))).thenReturn(pageResult);
        when(adsMapper.fromEntity(ad1)).thenReturn(response1);
        when(adsMapper.fromEntity(ad2)).thenReturn(response2);

        // Act
        AdsPage result = handler.handle(new GetAdsPageCommand(page, size));

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getAds().size());
        assertEquals(page, result.getPage());
        assertEquals(size, result.getSize());
        assertEquals(totalElements, result.getTotalElements());
        assertEquals(totalPages, result.getTotalPages());
        assertEquals(response1.getTitle(), result.getAds().get(0).getTitle());
        assertEquals(response2.getTitle(), result.getAds().get(1).getTitle());
    }

    @Test
    void shouldGetAdsByProviderUsername() {
        // Arrange
        int page = 0;
        int size = 10;
        long totalElements = 1;
        int totalPages = 1;
        String providerUsername = "testprovider";

        Provider provider = new Provider();
        provider.setUsername(providerUsername);

        Ad ad = new Ad();
        ad.setId(UUID.randomUUID());
        ad.setTitle("Provider Ad");
        ad.setDescription("Provider Description");
        ad.setRate(100);
        ad.setProvider(provider);

        List<Ad> ads = List.of(ad);
        Page<Ad> pageResult = new PageImpl<>(ads, PageRequest.of(page, size), totalElements);

        AdResponse response = new AdResponse(
            ad.getTitle(),
            null,
            providerUsername,
            ad.getRate(),
            ad.getId(),
            ad.getDescription(),
            null
        );

        when(adsRepository.findByProviderUsername(providerUsername, PageRequest.of(page, size))).thenReturn(pageResult);
        when(adsMapper.fromEntity(ad)).thenReturn(response);

        // Act
        AdsPage result = handler.handle(new GetAdsPageCommand(page, size, providerUsername));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getAds().size());
        assertEquals(page, result.getPage());
        assertEquals(size, result.getSize());
        assertEquals(totalElements, result.getTotalElements());
        assertEquals(totalPages, result.getTotalPages());
        assertEquals(response.getTitle(), result.getAds().get(0).getTitle());
        assertEquals(providerUsername, result.getAds().get(0).getProviderUsername());
    }
} 