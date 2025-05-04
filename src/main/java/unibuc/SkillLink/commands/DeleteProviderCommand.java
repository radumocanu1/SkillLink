package unibuc.SkillLink.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Provider;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class DeleteProviderCommand implements ICommand<Void> {
    UUID id;
}
