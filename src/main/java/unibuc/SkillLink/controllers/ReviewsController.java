package unibuc.SkillLink.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import unibuc.SkillLink.DTOs.ReviewDto;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.commands.GetCurrentUserCommand;
import unibuc.SkillLink.commands.clients.GetClientCommand;
import unibuc.SkillLink.commands.invoices.CreateInvoiceCommand;
import unibuc.SkillLink.commands.invoices.DeleteInvoiceCommand;
import unibuc.SkillLink.commands.invoices.GetInvoiceCommand;
import unibuc.SkillLink.commands.providers.GetProviderCommand;
import unibuc.SkillLink.commands.reviews.CreateReviewCommand;
import unibuc.SkillLink.commands.reviews.DeleteReviewCommand;
import unibuc.SkillLink.commands.reviews.GetReviewCommand;
import unibuc.SkillLink.models.*;

import java.util.UUID;

@Controller
public class ReviewsController {
    @Autowired
    IMediator mediator;

    @PostMapping("/review")
    public ResponseEntity<String> submitReview(@RequestBody ReviewDto reviewDto) {
        Client client = mediator.handle(new GetClientCommand(reviewDto.getClientUsername()));
        Provider provider = mediator.handle(new GetProviderCommand(reviewDto.getProviderUsername()));

        Review review = new Review();
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        review.setClient(client);
        review.setProvider(provider);

        mediator.handle(new CreateReviewCommand(review));
        return ResponseEntity.ok("Review submitted");
    }

    @PutMapping("/review/{id}")
    public ResponseEntity<?> updateReview(@PathVariable UUID id, @RequestBody ReviewDto dto,Authentication auth) {
        AppUser currentUser = mediator.handle(new GetCurrentUserCommand(auth));

        Review review = mediator.handle(new GetReviewCommand(id));

        if (!(currentUser instanceof Client typeClient) || !review.getClient().getId().equals(typeClient.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Operation not permitted");
        }

        review.setContent(dto.getContent());
        review.setStars(dto.getStars());
        mediator.handle(new CreateReviewCommand(review));
        return ResponseEntity.ok("Updated Review");
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<?>  deleteReview(@PathVariable UUID id, Authentication auth) {
        AppUser currentUser = mediator.handle(new GetCurrentUserCommand(auth));

        Review review = mediator.handle(new GetReviewCommand(id));

        if (!(currentUser instanceof Client typeClient) || !review.getClient().getId().equals(typeClient.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Operation not permitted");
        }
        mediator.handle(new DeleteReviewCommand(id));

        return ResponseEntity.ok("Deleted Review");
    }
}
