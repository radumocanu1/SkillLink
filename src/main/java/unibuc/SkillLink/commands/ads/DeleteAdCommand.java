package unibuc.SkillLink.commands.ads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class DeleteAdCommand implements ICommand<Void> {
    private UUID id;
    private String providerUsername;
} 