package unibuc.SkillLink.commands.providers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class DeleteProviderCommand implements ICommand<Void> {
    UUID id;
}
