package unibuc.SkillLink.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import unibuc.SkillLink.abstractions.ICommand;

@Data
@AllArgsConstructor
public class AreUsersLinkedCommand implements ICommand<Boolean> {
    String providerUsername;
    String clientUsername;

}
