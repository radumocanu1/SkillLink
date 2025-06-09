package unibuc.SkillLink.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import unibuc.SkillLink.abstractions.Mediator;
import unibuc.SkillLink.commands.AreUsersLinkedCommand;
import unibuc.SkillLink.commands.GetCurrentUserCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Provider;

import java.util.HashSet;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Mediator mediator;

    @Test
    @WithMockUser(username = "testclient", roles = {"CLIENT"})
    void shouldReturnClientProfile() throws Exception {
        Client client = new Client("Test", "Client", "testclient", null, new HashSet<>(), new HashSet<>());
        client.setId(UUID.randomUUID());
        
        Mockito.when(mediator.handle(any(GetCurrentUserCommand.class))).thenReturn(client);

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/profile"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "testprovider", roles = {"PROVIDER"})
    void shouldReturnProviderProfile() throws Exception {
        Provider provider = new Provider("Test", "Provider", "testprovider", 0, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        provider.setId(UUID.randomUUID());
        
        Mockito.when(mediator.handle(any(GetCurrentUserCommand.class))).thenReturn(provider);

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("provider/profile"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser
    void shouldCheckIfUsersAreLinked() throws Exception {
        String clientUsername = "testclient";
        String providerUsername = "testprovider";
        
        Mockito.when(mediator.handle(any(AreUsersLinkedCommand.class))).thenReturn(true);

        mockMvc.perform(get("/linked")
                .param("clientUsername", clientUsername)
                .param("providerUsername", providerUsername))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
} 