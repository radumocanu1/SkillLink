package unibuc.SkillLink.handlers.clients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unibuc.SkillLink.commands.clients.CreateClientCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.repositories.ClientsRepository;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateClientHandlerTest {

    @Mock
    private ClientsRepository clientsRepository;

    @InjectMocks
    private CreateClientHandler handler;

    @Test
    void shouldCreateClient() {
        Client client = new Client("Test", "Client", "testclient", null, new HashSet<>(), new HashSet<>());

        Client savedClient = new Client("Test", "Client", "testclient", null, new HashSet<>(), new HashSet<>());
        savedClient.setId(UUID.randomUUID());

        when(clientsRepository.save(any(Client.class))).thenReturn(savedClient);

        Client result = handler.handle(new CreateClientCommand(client));

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(client.getUsername(), result.getUsername());
        assertEquals(client.getFirstName(), result.getFirstName());
        assertEquals(client.getLastName(), result.getLastName());
        assertTrue(result.getBookings().isEmpty());
        assertTrue(result.getProviders().isEmpty());
    }

    @Test
    void shouldCreateClientWithProfilePicture() {
        Client client = new Client("Test", "Client", "testclient", "profile.jpg", new HashSet<>(), new HashSet<>());

        Client savedClient = new Client("Test", "Client", "testclient", "profile.jpg", new HashSet<>(), new HashSet<>());
        savedClient.setId(UUID.randomUUID());

        when(clientsRepository.save(any(Client.class))).thenReturn(savedClient);

        Client result = handler.handle(new CreateClientCommand(client));

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(client.getUsername(), result.getUsername());
        assertEquals(client.getFirstName(), result.getFirstName());
        assertEquals(client.getLastName(), result.getLastName());
        assertEquals(client.getProfilePicture(), result.getProfilePicture());
        assertTrue(result.getBookings().isEmpty());
        assertTrue(result.getProviders().isEmpty());
    }
} 