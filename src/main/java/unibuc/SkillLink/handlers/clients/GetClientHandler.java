package unibuc.SkillLink.handlers.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.clients.GetClientCommand;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.repositories.ClientsRepository;

import java.util.Optional;

@Component
public class GetClientHandler implements IHandler<GetClientCommand, Client> {

    @Autowired
    private ClientsRepository clientsRepository;

    @Override
    public Client handle(GetClientCommand getClientCommand) {
        Optional<Client> client;
        if (getClientCommand.getUsername() != null)
            client = clientsRepository.findByUsername(getClientCommand.getUsername());
        else
            client = clientsRepository.findById(getClientCommand.getId());

        if (client.isEmpty()) {
            throw new NotFoundException(
                    "Client",
                    getClientCommand.getId() == null ?
                            getClientCommand.getUsername() :
                            getClientCommand.getId().toString());
        }
        return client.get();
    }
}
