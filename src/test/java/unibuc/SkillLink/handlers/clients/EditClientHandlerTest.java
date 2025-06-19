package unibuc.SkillLink.handlers.clients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unibuc.SkillLink.commands.clients.EditClientCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.repositories.ClientsRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EditClientHandlerTest {

    @Mock
    private ClientsRepository clientsRepository;

    @InjectMocks
    private EditClientHandler handler;

    @Test
    void shouldUpdateAllClientFields() {
        UUID clientId = UUID.randomUUID();
        
        Client existingClient = new Client();
        existingClient.setId(clientId);
        existingClient.setUsername("oldusername");
        existingClient.setFirstName("OldFirst");
        existingClient.setLastName("OldLast");
        existingClient.setProfilePicture("old.jpg");

        Client updatedClient = new Client();
        updatedClient.setId(clientId);
        updatedClient.setUsername("newusername");
        updatedClient.setFirstName("NewFirst");
        updatedClient.setLastName("NewLast");
        updatedClient.setProfilePicture("new.jpg");

        when(clientsRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
        when(clientsRepository.save(any(Client.class))).thenReturn(updatedClient);

        Client result = handler.handle(new EditClientCommand(updatedClient));

        assertNotNull(result);
        assertEquals(clientId, result.getId());
        assertEquals("newusername", result.getUsername());
        assertEquals("NewFirst", result.getFirstName());
        assertEquals("NewLast", result.getLastName());
        assertEquals("new.jpg", result.getProfilePicture());
    }

    @Test
    void shouldUpdateOnlyProvidedFields() {
        UUID clientId = UUID.randomUUID();
        
        Client existingClient = new Client();
        existingClient.setId(clientId);
        existingClient.setUsername("oldusername");
        existingClient.setFirstName("OldFirst");
        existingClient.setLastName("OldLast");
        existingClient.setProfilePicture("old.jpg");

        Client partialUpdate = new Client();
        partialUpdate.setId(clientId);
        partialUpdate.setFirstName("NewFirst");
        partialUpdate.setProfilePicture("new.jpg");

        Client savedClient = new Client();
        savedClient.setId(clientId);
        savedClient.setUsername("oldusername");
        savedClient.setFirstName("NewFirst");
        savedClient.setLastName("OldLast");
        savedClient.setProfilePicture("new.jpg");

        when(clientsRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
        when(clientsRepository.save(any(Client.class))).thenReturn(savedClient);

        Client result = handler.handle(new EditClientCommand(partialUpdate));

        assertNotNull(result);
        assertEquals(clientId, result.getId());
        assertEquals("oldusername", result.getUsername());
        assertEquals("NewFirst", result.getFirstName());
        assertEquals("OldLast", result.getLastName());
        assertEquals("new.jpg", result.getProfilePicture());
    }

    @Test
    void shouldThrowExceptionWhenClientNotFound() {
        UUID clientId = UUID.randomUUID();
        
        Client updateClient = new Client();
        updateClient.setId(clientId);
        updateClient.setUsername("newusername");

        when(clientsRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(
            NoSuchElementException.class,
            () -> handler.handle(new EditClientCommand(updateClient)),
            "Client not found with ID: " + clientId
        );
    }

    @Test
    void shouldNotUpdateEmptyFields() {
        UUID clientId = UUID.randomUUID();
        
        Client existingClient = new Client();
        existingClient.setId(clientId);
        existingClient.setUsername("username");
        existingClient.setFirstName("First");
        existingClient.setLastName("Last");
        existingClient.setProfilePicture("profile.jpg");

        Client updateClient = new Client();
        updateClient.setId(clientId);
        updateClient.setUsername("");
        updateClient.setFirstName("");
        updateClient.setLastName("");

        Client savedClient = new Client();
        savedClient.setId(clientId);
        savedClient.setUsername("username");
        savedClient.setFirstName("First");
        savedClient.setLastName("Last");
        savedClient.setProfilePicture("profile.jpg");

        when(clientsRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
        when(clientsRepository.save(any(Client.class))).thenReturn(savedClient);

        Client result = handler.handle(new EditClientCommand(updateClient));

        assertNotNull(result);
        assertEquals(clientId, result.getId());
        assertEquals("username", result.getUsername());
        assertEquals("First", result.getFirstName());
        assertEquals("Last", result.getLastName());
        assertEquals("profile.jpg", result.getProfilePicture());
    }
} 