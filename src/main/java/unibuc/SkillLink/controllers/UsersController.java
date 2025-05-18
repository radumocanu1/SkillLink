package unibuc.SkillLink.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import unibuc.SkillLink.abstractions.Mediator;
import unibuc.SkillLink.commands.AreUsersLinkedCommand;
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

    @GetMapping("/linked")
    @ResponseBody
    public ResponseEntity<Boolean> isLinked(@RequestParam String clientUsername, @RequestParam String providerUsername) {
        Boolean result = mediator.handle(new AreUsersLinkedCommand(providerUsername, clientUsername));
        return ResponseEntity.ok(result);
    }
}
