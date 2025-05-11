package unibuc.SkillLink.commands.providers;

import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Provider;

import java.util.UUID;

@Getter
public class GetProviderCommand implements ICommand<Provider> {
    UUID id;
    String username;

    public GetProviderCommand(UUID id) {
        this.id = id;
    }

    public GetProviderCommand(String username) {
        this.username = username;
    }
}





