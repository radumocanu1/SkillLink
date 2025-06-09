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
import unibuc.SkillLink.commands.GetCurrentUserCommand;
import unibuc.SkillLink.commands.clients.*;
import unibuc.SkillLink.commands.providers.GetClientsCommand;
import unibuc.SkillLink.models.Client;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClientsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Mediator mediator;

    @Test
    @WithMockUser
    void shouldReturnAllClients() throws Exception {
        Client client = new Client("Test", "Client", "testclient", null, new HashSet<>(), new HashSet<>());
        client.setId(UUID.randomUUID());
        
        List<Client> clients = List.of(client);
        Mockito.when(mediator.handle(any(GetClientsCommand.class))).thenReturn(clients);

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/list"))
                .andExpect(model().attributeExists("clients"));
    }

    @Test
    @WithMockUser
    void shouldReturnClientByUsername() throws Exception {
        String username = "testclient";
        Client client = new Client("Test", "Client", username, null, new HashSet<>(), new HashSet<>());
        client.setId(UUID.randomUUID());
        
        Mockito.when(mediator.handle(any(GetClientCommand.class))).thenReturn(client);

        mockMvc.perform(get("/client/by-username/" + username))
                .andExpect(status().isOk())
                .andExpect(view().name("client/public-profile"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser
    void shouldReturnClientById() throws Exception {
        UUID clientId = UUID.randomUUID();
        Client client = new Client("Test", "Client", "testclient", null, new HashSet<>(), new HashSet<>());
        client.setId(clientId);
        
        Mockito.when(mediator.handle(any(GetClientCommand.class))).thenReturn(client);

        mockMvc.perform(get("/client/" + clientId))
                .andExpect(status().isOk())
                .andExpect(view().name("client/public-profile"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "testclient", roles = {"CLIENT"})
    void shouldDeleteClient() throws Exception {
        UUID clientId = UUID.randomUUID();
        Client client = new Client();
        client.setId(clientId);
        client.setUsername("testclient");
        
        Mockito.when(mediator.handle(any(GetCurrentUserCommand.class))).thenReturn(client);

        mockMvc.perform(delete("/client/" + clientId))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
} 