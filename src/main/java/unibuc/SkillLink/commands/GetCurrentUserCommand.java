package unibuc.SkillLink.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.Authentication;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.AppUser;

@Data
@AllArgsConstructor

public class GetCurrentUserCommand implements ICommand<AppUser> {
    Authentication authentication;
}
