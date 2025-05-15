package unibuc.SkillLink.commands.reviews;

import groovyjarjarpicocli.CommandLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Review;

@AllArgsConstructor
@Getter
public class CreateReviewCommand implements ICommand<Review> {
    Review review;
}
