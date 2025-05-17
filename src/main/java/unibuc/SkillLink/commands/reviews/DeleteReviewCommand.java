package unibuc.SkillLink.commands.reviews;

import groovyjarjarpicocli.CommandLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.ICommand;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class DeleteReviewCommand implements ICommand<Void> {
    UUID id;
}
