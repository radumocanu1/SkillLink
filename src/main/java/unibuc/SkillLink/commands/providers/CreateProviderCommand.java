package unibuc.SkillLink.commands.providers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Provider;


@Getter
@AllArgsConstructor
public class CreateProviderCommand implements ICommand<Provider> {
    Provider provider;
}
