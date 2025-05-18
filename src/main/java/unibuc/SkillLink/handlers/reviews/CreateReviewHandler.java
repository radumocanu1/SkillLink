package unibuc.SkillLink.handlers.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.abstractions.Mediator;
import unibuc.SkillLink.commands.AreUsersLinkedCommand;
import unibuc.SkillLink.commands.invoices.CreateInvoiceCommand;
import unibuc.SkillLink.commands.reviews.CreateReviewCommand;
import unibuc.SkillLink.exceptions.UsersNotLinkedException;
import unibuc.SkillLink.models.Invoice;
import unibuc.SkillLink.models.Review;
import unibuc.SkillLink.repositories.InvoicesRepository;
import unibuc.SkillLink.repositories.ReviewsRepository;

@Component
public class CreateReviewHandler implements IHandler<CreateReviewCommand, Review> {
    @Lazy
    @Autowired
    Mediator mediator;

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Override
    public Review handle(CreateReviewCommand cmd) {
        var linked = mediator.handle(new AreUsersLinkedCommand(cmd.getReview().getProvider().getUsername(), cmd.getReview().getClient().getUsername()));
        if (!linked)
            throw new UsersNotLinkedException("The users are not linked");

        return reviewsRepository.save(cmd.getReview());
    }
}

