package unibuc.SkillLink.commands.clients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Client;

import java.util.UUID;

@Getter
public class GetClientCommand implements ICommand<Client> {
    UUID id;
    String username;

    public GetClientCommand(UUID id) {
        this.id = id;
    }

    public GetClientCommand(String username) {
        this.username = username;
    }
}
