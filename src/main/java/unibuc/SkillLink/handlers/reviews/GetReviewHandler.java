package unibuc.SkillLink.handlers.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.invoices.GetInvoiceCommand;
import unibuc.SkillLink.commands.reviews.GetReviewCommand;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.models.Invoice;
import unibuc.SkillLink.models.Review;
import unibuc.SkillLink.repositories.InvoicesRepository;
import unibuc.SkillLink.repositories.ReviewsRepository;

import java.util.Optional;

@Component
public class GetReviewHandler implements IHandler<GetReviewCommand, Review> {
    @Autowired
    private ReviewsRepository reviewsRepository;

    @Override
    public Review handle(GetReviewCommand cmd) {
        Optional<Review> review;

        review = reviewsRepository.findById(cmd.getId());

        if (review.isEmpty()) {
            throw new NotFoundException(
                    "Review",
                    cmd.getId().toString());
        }
        return review.get();
    }
}
