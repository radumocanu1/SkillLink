package unibuc.SkillLink.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.commands.*;
import unibuc.SkillLink.models.Provider;

import java.util.UUID;

@Controller
public class ProvidersController {

    @Autowired
    IMediator mediator;

    @GetMapping("/provider/create")
    public String getCreateProvider(Model model) {
        return "provider/create";
    }

    @GetMapping("/providers")
    public String getAllProviders(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        var providers = mediator.handle(new GetProvidersCommand());
        model.addAttribute("providers", providers);
        return "providers";
    }

    @GetMapping("/provider/{id}")
    public String getProvider(@PathVariable UUID id, Model model) {
        var provider = mediator.handle(new GetProviderCommand(id));
        model.addAttribute("provider", provider);
        return "provider";
    }

    @PostMapping("/provider")
    public String createProvider(@ModelAttribute Provider provider, Model model) {
        var createdProvider = mediator.handle(new CreateProviderCommand(provider));
        model.addAttribute("provider", createdProvider);
        return "provider";
    }


    @DeleteMapping("/provider/{id}")
    public String deleteProvider(@PathVariable UUID id, Model model) {
        mediator.handle(new DeleteProviderCommand(id));
        return "home";
    }

    @PutMapping("/provider/{id}")
    public String editProvider(@PathVariable UUID id, @ModelAttribute Provider provider, Model model) {
        var editedProvider = mediator.handle(new EditProviderCommand(provider));
        model.addAttribute("provider", editedProvider);
        return "provider";
    }
}

