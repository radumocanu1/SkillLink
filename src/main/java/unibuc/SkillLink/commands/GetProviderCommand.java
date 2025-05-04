package unibuc.SkillLink.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Provider;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class GetProviderCommand implements ICommand<Provider> {
    UUID id;
}





