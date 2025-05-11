package unibuc.SkillLink.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;

@AllArgsConstructor
@Getter
public class RegisterCommand implements ICommand<Void> {
    String username;
    String password;
    String firstname;
    String lastname;
    String userType;
}
