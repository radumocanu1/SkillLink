package unibuc.SkillLink.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.GetClientCommand;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.repositories.ClientsRepository;

@Component
public class GetClientHandler implements IHandler<GetClientCommand, Client> {
    @Autowired
    private ClientsRepository clientsRepository;

    @Override
    public Client handle(GetClientCommand getClientCommand) {
        var provider = clientsRepository.findById(getClientCommand.getId());
        if (provider.isEmpty()) {
            throw new NotFoundException("Client", getClientCommand.getId().toString());
        }
        return provider.get();
    }
}
