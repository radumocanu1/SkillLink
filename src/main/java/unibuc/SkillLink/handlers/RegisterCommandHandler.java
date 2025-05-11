package unibuc.SkillLink.handlers;

import org.attoparser.ICommentHandler;
import org.attoparser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.commands.RegisterCommand;
import unibuc.SkillLink.helpers.EntityUserTypesMappings;
import unibuc.SkillLink.helpers.UserType;

@Component
public class RegisterCommandHandler implements IHandler<RegisterCommand, Void> {
    @Autowired
    @Lazy
    IMediator mediator;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserDetailsManager userDetailsManager;

    @Override
    public Void handle(RegisterCommand command){
        var createCommand = EntityUserTypesMappings.getCreateEntityCommand(
                UserType.fromString(command.getUserType()),
                command.getUsername(),
                command.getFirstname(),
                command.getLastname()
        );

        UserDetails user = User.builder()
                .username(command.getUsername())
                .password(encoder.encode(command.getPassword()))
                .roles(command.getUserType().toUpperCase())
                .build();

        userDetailsManager.createUser(user);
        mediator.handle(createCommand);

        return null;
    }
}
