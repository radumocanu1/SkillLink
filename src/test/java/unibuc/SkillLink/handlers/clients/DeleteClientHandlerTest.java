package unibuc.SkillLink.handlers.clients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unibuc.SkillLink.commands.clients.DeleteClientCommand;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.repositories.ClientsRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteClientHandlerTest {

    @Mock
    private ClientsRepository clientsRepository;

    @InjectMocks
    private DeleteClientHandler handler;

    @Test
    void shouldDeleteExistingClient() {
        UUID clientId = UUID.randomUUID();
        when(clientsRepository.existsById(clientId)).thenReturn(true);

        assertDoesNotThrow(() -> handler.handle(new DeleteClientCommand(clientId)));
        verify(clientsRepository).deleteById(clientId);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenClientDoesNotExist() {
        UUID clientId = UUID.randomUUID();
        when(clientsRepository.existsById(clientId)).thenReturn(false);

        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> handler.handle(new DeleteClientCommand(clientId))
        );

        assertEquals("Client", exception.getEntityName());
        assertEquals(clientId.toString(), exception.getId());
        verify(clientsRepository, never()).deleteById(any());
    }
} 