package unibuc.SkillLink.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Provider;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class EditProviderCommand implements ICommand<Provider> {
    Provider provider;
}
