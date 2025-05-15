package unibuc.SkillLink.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.commands.GetCurrentUserCommand;
import unibuc.SkillLink.commands.clients.GetClientCommand;
import unibuc.SkillLink.commands.providers.GetProviderCommand;
import unibuc.SkillLink.models.AppUser;

@Component
public class GetCurrentUserCommandHandler implements IHandler<GetCurrentUserCommand, AppUser>{

    @Autowired
    @Lazy
    IMediator mediator;

    @Override
    public AppUser handle(GetCurrentUserCommand command) {
        if (command.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CLIENT"))) {
            return mediator.handle(new GetClientCommand(command.getAuthentication().getName()));
        }
        return mediator.handle(new GetProviderCommand(command.getAuthentication().getName()));

    }
}
