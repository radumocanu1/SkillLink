package unibuc.SkillLink.handlers.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.clients.EditClientCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.repositories.ClientsRepository;

import java.util.NoSuchElementException;

@Component
public class EditClientHandler implements IHandler<EditClientCommand, Client> {
    @Autowired
    private ClientsRepository clientsRepository;

    public Client handle(EditClientCommand editClientCommand) {
        var sourceClient = editClientCommand.getClient();

        var existingClient = clientsRepository.findById(sourceClient.getId())
                .orElseThrow(() -> new NoSuchElementException("Client not found with ID: " + sourceClient.getId()));

        if (hasText(sourceClient.getFirstName())) {
            existingClient.setFirstName(sourceClient.getFirstName());
        }

        if (hasText(sourceClient.getLastName())) {
            existingClient.setLastName(sourceClient.getLastName());
        }

        if (hasText(sourceClient.getUsername())) {
            existingClient.setUsername(sourceClient.getUsername());
        }

        if (sourceClient.getProfilePicture() != null) {
            existingClient.setProfilePicture(sourceClient.getProfilePicture());
        }

        return clientsRepository.save(existingClient);
    }

    private boolean hasText(String value) {
        return StringUtils.hasText(value);
    }
}
