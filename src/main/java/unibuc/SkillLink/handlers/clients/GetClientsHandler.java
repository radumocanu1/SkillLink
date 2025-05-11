package unibuc.SkillLink.handlers.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.providers.GetClientsCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.repositories.ClientsRepository;

import java.util.List;

@Component
public class GetClientsHandler implements IHandler<GetClientsCommand, List<Client>> {
    @Autowired
    private ClientsRepository clientsRepository;

    @Override
    public List<Client> handle(GetClientsCommand getClientsCommand) {
        return clientsRepository.findAll();
    }
}
