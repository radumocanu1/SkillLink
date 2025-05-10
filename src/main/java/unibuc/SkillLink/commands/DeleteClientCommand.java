package unibuc.SkillLink.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteClientCommand implements ICommand<Void> {
    UUID id;
}
