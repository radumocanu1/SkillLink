package unibuc.SkillLink.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import unibuc.SkillLink.DTOs.ReviewDto;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.commands.clients.GetClientCommand;
import unibuc.SkillLink.commands.providers.GetProviderCommand;
import unibuc.SkillLink.commands.reviews.CreateReviewCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.models.Review;

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
}
