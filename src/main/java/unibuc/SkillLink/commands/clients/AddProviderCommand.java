package unibuc.SkillLink.commands.clients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Client;


@Getter
@AllArgsConstructor
public class AddProviderCommand implements ICommand<Client> {
    String clientUsername;
    String providerUsername;
}
