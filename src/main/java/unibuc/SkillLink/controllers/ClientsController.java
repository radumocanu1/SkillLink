package unibuc.SkillLink.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.commands.GetCurrentUserCommand;
import unibuc.SkillLink.commands.clients.*;
import unibuc.SkillLink.commands.providers.GetClientsCommand;
import unibuc.SkillLink.models.AppUser;
import unibuc.SkillLink.models.Client;

import java.util.Optional;
import java.util.UUID;

@Controller
public class ClientsController {
    @Autowired
    IMediator mediator;

    @PostMapping("/provider/add")
    public String addProvider(@RequestParam("clientUsername") String clientUsername, @RequestParam("providerUsername") String providerUsername, Model model, Authentication auth) {
        var client = mediator.handle(new AddProviderCommand(clientUsername, providerUsername));
        model.addAttribute("user", client);
        return "redirect:/profile";
    }

    @GetMapping("/clients")
    public String getAllClients(Model model) {
        var clients = mediator.handle(new GetClientsCommand());
        model.addAttribute("clients", clients);
        return "client/list";
    }

    @GetMapping("/client/by-username/{username}")
    public String getByUsername(Model model, @PathVariable String username) {
        var client = mediator.handle(new GetClientCommand(username));
        model.addAttribute("user", client);
        return "client/public-profile";
    }

    @GetMapping("/client/{id}")
    public String getClient(@PathVariable UUID id, Model model) {
        var client = mediator.handle(new GetClientCommand(id));
        model.addAttribute("user", client);
        return "client/public-profile";
    }

    @GetMapping("client/create")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new Client());
        return "client/create";
    }

    @PostMapping("/client/create")
    public String createClient(@ModelAttribute Client client, Model model) {
        var createdClient = mediator.handle(new CreateClientCommand(client));
        model.addAttribute("client", createdClient);
        return "client";
    }

    @DeleteMapping("/client/{id}")
    public String deleteClient(@PathVariable UUID id, Model model, Authentication auth) {
        AppUser currentUser = mediator.handle(new GetCurrentUserCommand(auth));

        if (!(currentUser instanceof Client typeClient) || !typeClient.getId().equals(id)) {
            model.addAttribute("status", 403);
            model.addAttribute("message", "Operation not permitted");
            return "error";
        }
        mediator.handle(new DeleteClientCommand(id));
        return "index";
    }

    @PutMapping("/client/{id}")
    public String editClient(@PathVariable UUID id, @ModelAttribute Client client, Model model, Authentication auth) {
        AppUser currentUser = mediator.handle(new GetCurrentUserCommand(auth));

        if (!(currentUser instanceof Client typeClient) || !typeClient.getId().equals(id)) {
            model.addAttribute("status", 403);
            model.addAttribute("message", "Operation not permitted");
            return "error";
        }

        var editedClient = mediator.handle(new EditClientCommand(client));
        model.addAttribute("user", editedClient);
        return "redirect:/profile";
    }
}
