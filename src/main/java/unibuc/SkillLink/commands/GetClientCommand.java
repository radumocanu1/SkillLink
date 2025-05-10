package unibuc.SkillLink.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Client;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class GetClientCommand implements ICommand<Client> {
    UUID id;
}
