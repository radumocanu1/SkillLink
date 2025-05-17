package unibuc.SkillLink.commands.reviews;

import groovyjarjarpicocli.CommandLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Review;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class GetReviewCommand implements ICommand<Review> {
    UUID id;
}
