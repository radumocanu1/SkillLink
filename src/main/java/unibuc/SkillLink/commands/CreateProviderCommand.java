package unibuc.SkillLink.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Provider;


@Getter
@AllArgsConstructor
public class CreateProviderCommand implements ICommand<Provider> {
    Provider provider;
}
