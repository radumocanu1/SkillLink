package unibuc.SkillLink.handlers.clients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unibuc.SkillLink.commands.providers.GetClientsCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.repositories.ClientsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetClientsHandlerTest {

    @Mock
    private ClientsRepository clientsRepository;

    @InjectMocks
    private GetClientsHandler handler;

    @Test
    void shouldReturnAllClients() {
        List<Client> expectedClients = new ArrayList<>();
        
        Client client1 = new Client();
        client1.setId(UUID.randomUUID());
        client1.setUsername("client1");
        expectedClients.add(client1);

        Client client2 = new Client();
        client2.setId(UUID.randomUUID());
        client2.setUsername("client2");
        expectedClients.add(client2);

        when(clientsRepository.findAll()).thenReturn(expectedClients);

        List<Client> result = handler.handle(new GetClientsCommand());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedClients, result);
    }

    @Test
    void shouldReturnEmptyListWhenNoClients() {
        List<Client> expectedClients = new ArrayList<>();
        when(clientsRepository.findAll()).thenReturn(expectedClients);

        List<Client> result = handler.handle(new GetClientsCommand());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
} 