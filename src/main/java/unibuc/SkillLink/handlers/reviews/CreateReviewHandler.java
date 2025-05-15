package unibuc.SkillLink.handlers.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.invoices.CreateInvoiceCommand;
import unibuc.SkillLink.commands.reviews.CreateReviewCommand;
import unibuc.SkillLink.models.Invoice;
import unibuc.SkillLink.models.Review;
import unibuc.SkillLink.repositories.InvoicesRepository;
import unibuc.SkillLink.repositories.ReviewsRepository;

@Component
public class CreateReviewHandler implements IHandler<CreateReviewCommand, Review> {
    @Autowired
    private ReviewsRepository reviewsRepository;

    @Override
    public Review handle(CreateReviewCommand cmd) {
        return reviewsRepository.save(cmd.getReview());
    }
}

