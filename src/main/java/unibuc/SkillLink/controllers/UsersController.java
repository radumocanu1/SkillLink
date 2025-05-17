package unibuc.SkillLink.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import unibuc.SkillLink.abstractions.Mediator;
import unibuc.SkillLink.commands.GetCurrentUserCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Provider;

@Controller
public class UsersController {
    @Autowired
    Mediator mediator;

    @GetMapping("/profile")
    public String profile(Model model, Authentication auth) {
        var user = mediator.handle(new GetCurrentUserCommand(auth));
        model.addAttribute("user", user);

        if (user instanceof Client) {
            return "client/profile";
        }
        else if (user instanceof Provider) {
            return "provider/profile";
        }
        return "/";
    }
}
