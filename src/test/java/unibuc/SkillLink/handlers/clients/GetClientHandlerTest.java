package unibuc.SkillLink.handlers.clients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unibuc.SkillLink.commands.clients.GetClientCommand;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.repositories.ClientsRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetClientHandlerTest {

    @Mock
    private ClientsRepository clientsRepository;

    @InjectMocks
    private GetClientHandler handler;

    @Test
    void shouldGetClientById() {
        UUID clientId = UUID.randomUUID();
        Client expectedClient = new Client("Test", "Client", "testclient", null, new HashSet<>(), new HashSet<>());
        expectedClient.setId(clientId);

        when(clientsRepository.findById(clientId)).thenReturn(Optional.of(expectedClient));

        Client result = handler.handle(new GetClientCommand(clientId));

        assertNotNull(result);
        assertEquals(clientId, result.getId());
        assertEquals("testclient", result.getUsername());
    }

    @Test
    void shouldGetClientByUsername() {
        String username = "testclient";
        Client expectedClient = new Client("Test", "Client", username, null, new HashSet<>(), new HashSet<>());
        expectedClient.setId(UUID.randomUUID());

        when(clientsRepository.findByUsername(username)).thenReturn(Optional.of(expectedClient));

        Client result = handler.handle(new GetClientCommand(username));

        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenClientIdNotFound() {
        UUID clientId = UUID.randomUUID();

        when(clientsRepository.findById(clientId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> handler.handle(new GetClientCommand(clientId))
        );

        assertEquals("Client", exception.getEntityName());
        assertEquals(clientId.toString(), exception.getId());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenClientUsernameNotFound() {
        String username = "nonexistent";

        when(clientsRepository.findByUsername(username)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> handler.handle(new GetClientCommand(username))
        );

        assertEquals("Client", exception.getEntityName());
        assertEquals(username, exception.getId());
    }
} 