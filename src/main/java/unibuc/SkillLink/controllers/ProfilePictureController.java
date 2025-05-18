package unibuc.SkillLink.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.commands.GetCurrentUserCommand;
import unibuc.SkillLink.commands.clients.EditClientCommand;
import unibuc.SkillLink.commands.providers.EditProviderCommand;
import unibuc.SkillLink.models.AppUser;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.services.ProfilePictureService;

import java.util.UUID;

@Controller
public class ProfilePictureController {

    @Autowired
    private ProfilePictureService profilePictureService;

    @Autowired
    private IMediator mediator;

    /**
     * Handle profile picture upload for providers
     */
    @PostMapping("/provider/profile-picture/{id}")
    public String uploadProviderProfilePicture(
            @PathVariable UUID id,
            @RequestParam("profilePicture") MultipartFile file,
            Authentication auth,
            Model model) {
        
        AppUser currentUser = mediator.handle(new GetCurrentUserCommand(auth));
        if (!(currentUser instanceof Provider provider) || !provider.getId().equals(id)) {
            model.addAttribute("status", 403);
            model.addAttribute("message", "Operation not permitted");
            return "error";
        }
        
        try {
            String filename = profilePictureService.saveProfilePicture(file, provider.getUsername());
            
            // Update the provider with the profile picture filename
            provider.setProfilePicture(filename);
            mediator.handle(new EditProviderCommand(provider));
            
            return "redirect:/profile";
        } catch (Exception e) {
            model.addAttribute("status", 500);
            model.addAttribute("message", "Failed to upload profile picture: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Handle profile picture upload for clients
     */
    @PostMapping("/client/profile-picture/{id}")
    public String uploadClientProfilePicture(
            @PathVariable UUID id,
            @RequestParam("profilePicture") MultipartFile file,
            Authentication auth,
            Model model) {
        
        AppUser currentUser = mediator.handle(new GetCurrentUserCommand(auth));
        if (!(currentUser instanceof Client client) || !client.getId().equals(id)) {
            model.addAttribute("status", 403);
            model.addAttribute("message", "Operation not permitted");
            return "error";
        }
        
        try {
            String filename = profilePictureService.saveProfilePicture(file, client.getUsername());
            
            client.setProfilePicture(filename);
            mediator.handle(new EditClientCommand(client));
            
            return "redirect:/profile";
        } catch (Exception e) {
            model.addAttribute("status", 500);
            model.addAttribute("message", "Failed to upload profile picture: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Handle profile picture deletion for providers
     */
    @DeleteMapping("/provider/profile-picture/{id}")
    public String deleteProviderProfilePicture(
            @PathVariable UUID id,
            Authentication auth,
            Model model) {
        
        AppUser currentUser = mediator.handle(new GetCurrentUserCommand(auth));
        if (!(currentUser instanceof Provider provider) || !provider.getId().equals(id)) {
            model.addAttribute("status", 403);
            model.addAttribute("message", "Operation not permitted");
            return "error";
        }
        
        try {
            if (provider.getProfilePicture() != null) {
                profilePictureService.deleteProfilePicture(provider.getProfilePicture());
                
                provider.setProfilePicture(null);
                mediator.handle(new EditProviderCommand(provider));
            }
            
            return "redirect:/profile";
        } catch (Exception e) {
            model.addAttribute("status", 500);
            model.addAttribute("message", "Failed to delete profile picture: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Handle profile picture deletion for clients
     */
    @DeleteMapping("/client/profile-picture/{id}")
    public String deleteClientProfilePicture(
            @PathVariable UUID id,
            Authentication auth,
            Model model) {
        
        AppUser currentUser = mediator.handle(new GetCurrentUserCommand(auth));
        if (!(currentUser instanceof Client client) || !client.getId().equals(id)) {
            model.addAttribute("status", 403);
            model.addAttribute("message", "Operation not permitted");
            return "error";
        }
        
        try {
            if (client.getProfilePicture() != null) {
                profilePictureService.deleteProfilePicture(client.getProfilePicture());
                
                client.setProfilePicture(null);
                mediator.handle(new EditClientCommand(client));
            }
            
            return "redirect:/profile";
        } catch (Exception e) {
            model.addAttribute("status", 500);
            model.addAttribute("message", "Failed to delete profile picture: " + e.getMessage());
            return "error";
        }
    }
}