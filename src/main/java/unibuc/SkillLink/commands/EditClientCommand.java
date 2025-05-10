package unibuc.SkillLink.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Client;

@Getter
@AllArgsConstructor
public class EditClientCommand implements ICommand<Client> {
    Client client;
}
