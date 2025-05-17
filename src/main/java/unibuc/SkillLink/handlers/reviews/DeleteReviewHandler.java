package unibuc.SkillLink.handlers.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.invoices.DeleteInvoiceCommand;
import unibuc.SkillLink.commands.reviews.DeleteReviewCommand;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.repositories.InvoicesRepository;
import unibuc.SkillLink.repositories.ReviewsRepository;

@Component
public class DeleteReviewHandler implements IHandler<DeleteReviewCommand, Void> {
    @Autowired
    private ReviewsRepository reviewsRepository;

    public Void handle(DeleteReviewCommand cmd) {
        var id = cmd.getId();
        if (!reviewsRepository.existsById(id)) {
            throw new NotFoundException("Review",id.toString());
        }

        reviewsRepository.deleteById(id);
        return null;
    }
}
