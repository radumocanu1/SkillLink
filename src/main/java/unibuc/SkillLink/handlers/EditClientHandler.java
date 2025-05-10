package unibuc.SkillLink.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.EditClientCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.repositories.ClientsRepository;

@Component
public class EditClientHandler implements IHandler<EditClientCommand, Client> {
    @Autowired
    private ClientsRepository clientsRepository;

    public Client handle(EditClientCommand editClientCommand) {
        var client = editClientCommand.getClient();

        return clientsRepository.save(client);
    }
}
