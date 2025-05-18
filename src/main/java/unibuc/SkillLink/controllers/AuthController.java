package unibuc.SkillLink.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import unibuc.SkillLink.DTOs.AuthResponse;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.commands.RegisterCommand;

@Controller
public class AuthController {

    @Autowired
    IMediator mediator;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/signup/client")
    public String signupClient(Model model) {
        model.addAttribute("userType", "client");
        return "auth/signup";
    }

    @GetMapping("/signup/provider")
    public String signupProvider(Model model) {
        model.addAttribute("userType", "provider");
        return "auth/signup";
    }

    @PostMapping("/signup")
    @Transactional
    public String processSignup(@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String userType) {

        mediator.handle(new RegisterCommand(
                username, password, firstName, lastName, userType
        ));
        return "redirect:/login?success";
    }

    @GetMapping("/authenticated")
    @ResponseBody
    public AuthResponse isAuthenticated(Authentication authentication) {
        var authenticated = authentication != null && authentication.isAuthenticated();
        String role = null;
        if (authenticated){
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CLIENT")))
                role = "CLIENT";
            else
                role = "PROVIDER";
        }
        return new AuthResponse(authenticated,role);
    }
}
