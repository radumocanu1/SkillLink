package unibuc.SkillLink.handlers.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.clients.CreateClientCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.repositories.ClientsRepository;


@Component
public class CreateClientHandler implements IHandler<CreateClientCommand, Client> {
    @Autowired
    private ClientsRepository clientsRepository;

    public Client handle(CreateClientCommand createProviderCommand) {
        return clientsRepository.save(createProviderCommand.getClient());
    }
}
