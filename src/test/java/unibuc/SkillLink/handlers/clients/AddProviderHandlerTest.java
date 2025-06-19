package unibuc.SkillLink.handlers.clients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.commands.clients.AddProviderCommand;
import unibuc.SkillLink.commands.clients.GetClientCommand;
import unibuc.SkillLink.commands.providers.GetProviderCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.ClientsRepository;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddProviderHandlerTest {

    @Mock
    private IMediator mediator;

    @Mock
    private ClientsRepository repository;

    @InjectMocks
    private AddProviderHandler handler;

    @Test
    void shouldAddProviderToClient() {
        String clientUsername = "testclient";
        String providerUsername = "testprovider";

        Client client = new Client("Test", "Client", clientUsername, null, new HashSet<>(), new HashSet<>());
        client.setId(UUID.randomUUID());

        Provider provider = new Provider("Test", "Provider", providerUsername, 0, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        provider.setId(UUID.randomUUID());

        when(mediator.handle(any(GetProviderCommand.class))).thenReturn(provider);
        when(mediator.handle(any(GetClientCommand.class))).thenReturn(client);
        when(repository.save(client)).thenReturn(client);

        Client result = handler.handle(new AddProviderCommand(clientUsername, providerUsername));

        assertNotNull(result);
        assertEquals(clientUsername, result.getUsername());
        assertTrue(result.getProviders().contains(provider));
        verify(repository).save(client);
    }

    @Test
    void shouldAddProviderToClientWithExistingProviders() {
        String clientUsername = "testclient";
        String providerUsername = "testprovider";

        Provider existingProvider = new Provider("Existing", "Provider", "existingprovider", 0, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        existingProvider.setId(UUID.randomUUID());

        Client client = new Client("Test", "Client", clientUsername, null, new HashSet<>(), new HashSet<>());
        client.setId(UUID.randomUUID());
        client.addProvider(existingProvider);

        Provider newProvider = new Provider("Test", "Provider", providerUsername, 0, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        newProvider.setId(UUID.randomUUID());

        when(mediator.handle(any(GetProviderCommand.class))).thenReturn(newProvider);
        when(mediator.handle(any(GetClientCommand.class))).thenReturn(client);
        when(repository.save(client)).thenReturn(client);

        Client result = handler.handle(new AddProviderCommand(clientUsername, providerUsername));

        assertNotNull(result);
        assertEquals(clientUsername, result.getUsername());
        assertEquals(2, result.getProviders().size());
        assertTrue(result.getProviders().contains(existingProvider));
        assertTrue(result.getProviders().contains(newProvider));
        verify(repository).save(client);
    }

    @Test
    void shouldNotDuplicateProviderIfAlreadyAdded() {
        String clientUsername = "testclient";
        String providerUsername = "testprovider";

        Provider provider = new Provider("Test", "Provider", providerUsername, 0, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        provider.setId(UUID.randomUUID());

        Client client = new Client("Test", "Client", clientUsername, null, new HashSet<>(), new HashSet<>());
        client.setId(UUID.randomUUID());
        client.addProvider(provider);

        when(mediator.handle(any(GetProviderCommand.class))).thenReturn(provider);
        when(mediator.handle(any(GetClientCommand.class))).thenReturn(client);
        when(repository.save(client)).thenReturn(client);

        Client result = handler.handle(new AddProviderCommand(clientUsername, providerUsername));

        assertNotNull(result);
        assertEquals(clientUsername, result.getUsername());
        assertEquals(1, result.getProviders().size());
        assertTrue(result.getProviders().contains(provider));
        verify(repository).save(client);
    }
} 