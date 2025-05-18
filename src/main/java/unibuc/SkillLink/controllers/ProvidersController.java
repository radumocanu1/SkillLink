package unibuc.SkillLink.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import unibuc.SkillLink.DTOs.ReviewDto;
import unibuc.SkillLink.DTOs.providers.UpdateRateRequest;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.annotations.Authorized;
import unibuc.SkillLink.annotations.SetRoles;
import unibuc.SkillLink.commands.GetCurrentUserCommand;
import unibuc.SkillLink.commands.providers.*;
import unibuc.SkillLink.exceptions.ForbiddenException;
import unibuc.SkillLink.models.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ProvidersController {

    @Autowired
    IMediator mediator;

    @GetMapping("/provider/create")
    public String getCreateProvider(Model model) {
        return "provider/create";
    }

    @Authorized(authority = "CLIENT")
    @GetMapping("/providers")
    public String getAllProviders(Model model, Authentication authentication) {
        var providers = mediator.handle(new GetProvidersCommand());
        model.addAttribute("providers", providers);
        model.addAttribute("authentication", authentication);

        return "provider/list";
    }

    @SetRoles
    @Transactional
    @GetMapping("/provider/by-username/{username}")
    public String getByUsername(@PathVariable String username, Model model, Authentication authentication) {
        var provider = mediator.handle(new GetProviderCommand(username));
        model.addAttribute("user", provider);
        model.addAttribute("authentication", authentication);
        model.addAttribute("reviews", getReviews(provider));
        return "provider/public-profile";
    }

    @GetMapping("/provider/{id}")
    @SetRoles
    @Transactional
    public String getProvider(@PathVariable UUID id, Model model,Authentication authentication) {
        var provider = mediator.handle(new GetProviderCommand(id));

        model.addAttribute("user", provider);
        model.addAttribute("authentication", authentication);
        model.addAttribute("reviews", getReviews(provider));
        return "provider/public-profile";
    }

    @PostMapping("/provider")
    public String createProvider(@ModelAttribute Provider provider, Model model) {
        var createdProvider = mediator.handle(new CreateProviderCommand(provider));
        model.addAttribute("provider", createdProvider);
        model.addAttribute("newSlot", new BookingDetails());

        return "/profile";
    }


    @DeleteMapping("/provider/{id}")
    public String deleteProvider(@PathVariable UUID id, Model model, Authentication auth) {
        AppUser currentUser = mediator.handle(new GetCurrentUserCommand(auth));

        if (!(currentUser instanceof Provider typeProvider) || !typeProvider.getId().equals(id)) {
            model.addAttribute("status", 403);
            model.addAttribute("message", "Operation not permitted");
            return "error";
        }
        mediator.handle(new DeleteProviderCommand(id));
         return "index";
    }

    @PutMapping("/provider/{id}")
    public String editProvider(@PathVariable UUID id, @ModelAttribute Provider provider, Model model, Authentication auth) {
        AppUser currentUser = mediator.handle(new GetCurrentUserCommand(auth));

        if (!(currentUser instanceof Provider typeProvider) || !typeProvider.getId().equals(id)) {
          throw new ForbiddenException();
        }

        var editedProvider = mediator.handle(new EditProviderCommand(provider));
        model.addAttribute("user", editedProvider);
        return "redirect:/profile";
    }

    @PostMapping("/provider/{id}/rate")
    public ResponseEntity<String> updateRate(@PathVariable UUID id, @RequestBody UpdateRateRequest request, Authentication auth) {
        AppUser currentUser = mediator.handle(new GetCurrentUserCommand(auth));

        if (!(currentUser instanceof Provider provider) || !provider.getId().equals(id)) {
            return ResponseEntity.status(403).body("Operation not permitted");
        }

        try {
            provider.setRate(request.getRate());
            mediator.handle(new EditProviderCommand(provider));
            return ResponseEntity.ok("Rate updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update rate: " + e.getMessage());
        }
    }

    private List<ReviewDto> getReviews(Provider provider){
        return provider.getReviews().stream()
                .map(review -> new ReviewDto(
                        review.getContent(),
                        review.getStars(),
                        review.getClient() != null ? review.getClient().getUsername() : "Unknown",
                        review.getId()
                ))
                .collect(Collectors.toList());
    }

}

