package unibuc.SkillLink.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.abstractions.Mediator;
import unibuc.SkillLink.commands.AreUsersLinkedCommand;
import unibuc.SkillLink.commands.providers.GetProviderCommand;

@Component
public class AreUsersLinkedCommandHandler implements IHandler<AreUsersLinkedCommand, Boolean> {
    @Lazy
    @Autowired
    Mediator mediator;

    @Override
    public Boolean handle(AreUsersLinkedCommand command) {
        var provider = mediator.handle(new GetProviderCommand(command.getProviderUsername()));
        for (var client : provider.getClients()) {
            if (client.getUsername().equals(command.getClientUsername()))
                return true;
        }
        return false;
    }
}
