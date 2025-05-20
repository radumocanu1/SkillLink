package unibuc.SkillLink.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import unibuc.SkillLink.DTOs.AuthResponse;
import unibuc.SkillLink.DTOs.SignupForm;
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
        SignupForm form = new SignupForm();
        form.setUserType("client");
        model.addAttribute("signupForm", form);
        return "auth/signup";
    }

    @GetMapping("/signup/provider")
    public String signupProvider(Model model) {
        SignupForm form = new SignupForm();
        form.setUserType("provider");
        model.addAttribute("signupForm", form);
        return "auth/signup";
    }

    @PostMapping("/signup")
    @Transactional
    public String processSignup(@ModelAttribute @Valid SignupForm signupForm,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "auth/signup";
        }

        mediator.handle(new RegisterCommand(
                signupForm.getUsername(),
                signupForm.getPassword(),
                signupForm.getFirstName(),
                signupForm.getLastName(),
                signupForm.getUserType()
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
