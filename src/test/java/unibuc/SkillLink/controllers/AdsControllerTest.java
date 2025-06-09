package unibuc.SkillLink.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import unibuc.SkillLink.DTOs.ads.AdResponse;
import unibuc.SkillLink.DTOs.ads.AdsPage;
import unibuc.SkillLink.abstractions.Mediator;
import unibuc.SkillLink.commands.ads.*;
import unibuc.SkillLink.services.AdPictureService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AdsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Mediator mediator;

    @MockitoBean
    private AdPictureService adPictureService;

    @Test
    @WithMockUser(username = "testclient", roles = {"CLIENT"})
    void shouldReturnAdsPage() throws Exception {
        // Arrange
        int page = 0;
        int size = 10;
        AdResponse adResponse = new AdResponse("Test Ad", "profile.jpg", "testprovider", 100, UUID.randomUUID(), "Description", "ad.jpg");
        List<AdResponse> ads = List.of(adResponse);
        AdsPage adsPage = new AdsPage(ads, page, size, 1, 1);
        
        Mockito.when(mediator.handle(any(GetAdsPageCommand.class))).thenReturn(adsPage);

        // Act & Assert
        mockMvc.perform(get("/ads")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(view().name("ads/list"))
                .andExpect(model().attributeExists("ads"))
                .andExpect(model().attributeExists("adsPage"));
    }

    @Test
    @WithMockUser(username = "testprovider", roles = {"PROVIDER"})
    void shouldReturnCreateAdPage() throws Exception {
        mockMvc.perform(get("/ads/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("ads/create"))
                .andExpect(model().attributeExists("authentication"));
    }

    @Test
    @WithMockUser(username = "testprovider", roles = {"PROVIDER"})
    void shouldCreateNewAd() throws Exception {
        String username = "testprovider";
        String requestJson = """
                {
                    "title": "Professional Web Development Services",
                    "description": "I am a professional web developer with over 5 years of experience in building modern web applications. I specialize in React, Angular, and Node.js development. I can help you create responsive, user-friendly websites that meet your business needs.",
                    "rate": 100,
                    "picture": null
                }""";
        
        AdResponse expectedResponse = new AdResponse(
            "Professional Web Development Services",
            null,
            username,
            100,
            UUID.randomUUID(),
            "I am a professional web developer with over 5 years of experience in building modern web applications. I specialize in React, Angular, and Node.js development. I can help you create responsive, user-friendly websites that meet your business needs.",
            null
        );
        Mockito.when(mediator.handle(any(CreateAdCommand.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/ads")
                .param("username", username)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(result -> {
                    System.out.println("Request Content: " + requestJson);
                    System.out.println("Response Status: " + result.getResponse().getStatus());
                    System.out.println("Response Content: " + result.getResponse().getContentAsString());
                })
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void shouldReturnAdDetails() throws Exception {
        // Arrange
        UUID adId = UUID.randomUUID();
        AdResponse adResponse = new AdResponse("Test Ad", "profile.jpg", "testprovider", 100, adId, "Description", "ad.jpg");
        
        Mockito.when(mediator.handle(any(GetAdCommand.class))).thenReturn(adResponse);

        // Act & Assert
        mockMvc.perform(get("/ads/" + adId))
                .andExpect(status().isOk())
                .andExpect(view().name("ads/ad-details"))
                .andExpect(model().attributeExists("ad"));
    }

    @Test
    @WithMockUser(username = "testprovider", roles = {"PROVIDER"})
    void shouldReturnMyAds() throws Exception {
        // Arrange
        AdResponse adResponse = new AdResponse("Test Ad", "profile.jpg", "testprovider", 100, UUID.randomUUID(), "Description", "ad.jpg");
        List<AdResponse> ads = List.of(adResponse);
        AdsPage adsPage = new AdsPage(ads, 0, 10, 1, 1);
        
        Mockito.when(mediator.handle(any(GetAdsPageCommand.class))).thenReturn(adsPage);

        // Act & Assert
        mockMvc.perform(get("/ads/my-ads"))
                .andExpect(status().isOk())
                .andExpect(view().name("provider/my-ads"))
                .andExpect(model().attributeExists("ads"));
    }

    @Test
    @WithMockUser(username = "testprovider", roles = {"PROVIDER"})
    void shouldDeleteAd() throws Exception {
        // Arrange
        UUID adId = UUID.randomUUID();
        String username = "testprovider";

        // Act & Assert
        mockMvc.perform(delete("/ads/" + adId)
                .with(request -> {
                    request.setRemoteUser(username);
                    return request;
                }))
                .andExpect(status().isOk());

        Mockito.verify(mediator).handle(any(DeleteAdCommand.class));
    }

    @Test
    @WithMockUser(username = "testprovider", roles = {"PROVIDER"})
    void shouldReturnEditAdPage() throws Exception {
        UUID adId = UUID.randomUUID();
        AdResponse adResponse = new AdResponse("Test Ad", "profile.jpg", "testprovider", 100, adId, "Description", "ad.jpg");
        
        Mockito.when(mediator.handle(any(GetAdCommand.class))).thenReturn(adResponse);

        mockMvc.perform(get("/ads/" + adId + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("ads/edit"))
                .andExpect(model().attributeExists("ad"))
                .andExpect(model().attributeExists("authentication"));
    }

    @Test
    @WithMockUser(username = "testprovider", roles = {"PROVIDER"})
    void shouldUpdateAd() throws Exception {
        UUID adId = UUID.randomUUID();
        String username = "testprovider";
        String requestJson = """
                {
                    "title": "Updated Professional Web Development Services",
                    "description": "I am a professional web developer with over 5 years of experience in building modern web applications. I specialize in React, Angular, and Node.js development. I can help you create responsive, user-friendly websites that meet your business needs. I also offer maintenance and support services.",
                    "rate": 150,
                    "picture": null
                }""";
        
        AdResponse expectedResponse = new AdResponse(
            "Updated Professional Web Development Services",
            null,
            username,
            150,
            adId,
            "I am a professional web developer with over 5 years of experience in building modern web applications. I specialize in React, Angular, and Node.js development. I can help you create responsive, user-friendly websites that meet your business needs. I also offer maintenance and support services.",
            null
        );
        Mockito.when(mediator.handle(any(UpdateAdCommand.class))).thenReturn(expectedResponse);

        mockMvc.perform(put("/ads/{id}", adId)
                .param("username", username)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }
} 