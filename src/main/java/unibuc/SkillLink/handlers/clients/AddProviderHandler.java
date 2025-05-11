package unibuc.SkillLink.handlers.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.commands.clients.AddProviderCommand;
import unibuc.SkillLink.commands.clients.GetClientCommand;
import unibuc.SkillLink.commands.providers.GetProviderCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.repositories.ClientsRepository;

@Component
public class AddProviderHandler implements IHandler<AddProviderCommand, Client>{

    @Autowired
    @Lazy
    IMediator mediator;

    @Autowired
    ClientsRepository repository;

    @Override
    public Client handle(AddProviderCommand command) {
        var provider = mediator.handle(new GetProviderCommand(command.getProviderUsername()));
        var client = mediator.handle(new GetClientCommand(command.getClientUsername()));
        client.addProvider(provider);
        repository.save(client);
        return client;
    }

}
