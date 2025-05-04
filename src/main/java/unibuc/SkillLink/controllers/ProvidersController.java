package unibuc.SkillLink.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.commands.GetProvidersCommand;

@Controller
public class ProvidersController {

    @Autowired
    IMediator mediator;

    @GetMapping("/providers")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        var providers = mediator.handle(new GetProvidersCommand());
        model.addAttribute("providers", providers);
        return "providers";
    }
}
