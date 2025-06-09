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
import unibuc.SkillLink.DTOs.providers.UpdateRateRequest;
import unibuc.SkillLink.abstractions.Mediator;
import unibuc.SkillLink.commands.GetCurrentUserCommand;
import unibuc.SkillLink.commands.providers.*;
import unibuc.SkillLink.models.Provider;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProvidersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Mediator mediator;

    @Test
    @WithMockUser(username = "testclient", roles = {"CLIENT"})
    void shouldReturnAllProviders() throws Exception {
        Provider provider = new Provider("Test", "Provider", "testprovider", 0, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        provider.setId(UUID.randomUUID());
        
        List<Provider> providers = List.of(provider);
        Mockito.when(mediator.handle(any(GetProvidersCommand.class))).thenReturn(providers);

        mockMvc.perform(get("/providers"))
                .andExpect(status().isOk())
                .andExpect(view().name("provider/list"))
                .andExpect(model().attributeExists("providers"))
                .andExpect(model().attributeExists("authentication"));
    }

    @Test
    @WithMockUser
    void shouldReturnProviderByUsername() throws Exception {
        String username = "testprovider";
        Provider provider = new Provider("Test", "Provider", username, 0, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        provider.setId(UUID.randomUUID());
        
        Mockito.when(mediator.handle(any(GetProviderCommand.class))).thenReturn(provider);

        mockMvc.perform(get("/provider/by-username/" + username))
                .andExpect(status().isOk())
                .andExpect(view().name("provider/public-profile"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("authentication"))
                .andExpect(model().attributeExists("reviews"));
    }

    @Test
    @WithMockUser(username = "testprovider", roles = {"PROVIDER"})
    void shouldUpdateProviderRate() throws Exception {
        UUID providerId = UUID.randomUUID();
        Provider provider = new Provider("Test", "Provider", "testprovider", 0, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        provider.setId(providerId);
        
        String requestJson = """
                {
                    "rate": 0.8
                }""";
        
        Mockito.when(mediator.handle(any(GetCurrentUserCommand.class))).thenReturn(provider);
        Mockito.when(mediator.handle(any(EditProviderCommand.class))).thenReturn(provider);

        mockMvc.perform(post("/provider/" + providerId + "/rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Rate updated successfully"));
    }

    @Test
    @WithMockUser(username = "testprovider", roles = {"PROVIDER"})
    void shouldDeleteProvider() throws Exception {
        UUID providerId = UUID.randomUUID();
        Provider provider = new Provider("Test", "Provider", "testprovider", 0, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        provider.setId(providerId);
        
        Mockito.when(mediator.handle(any(GetCurrentUserCommand.class))).thenReturn(provider);

        mockMvc.perform(delete("/provider/" + providerId))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

} 