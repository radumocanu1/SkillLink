package unibuc.SkillLink.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.commands.*;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Provider;

import java.util.UUID;

@Controller
public class ClientsController {
    @Autowired
    IMediator mediator;

    @GetMapping("/clients")
    public String getAllClients(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        var clients = mediator.handle(new GetClientsCommand());
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/client/{id}")
    public String getClient(@PathVariable UUID id, Model model) {
        var client = mediator.handle(new GetClientCommand(id));
        model.addAttribute("client", client);
        return "client";
    }

    @PostMapping("/client")
    public String createClient(@ModelAttribute Client client, Model model) {
        var createdClient = mediator.handle(new CreateClientCommand(client));
        model.addAttribute("client", createdClient);
        return "client";
    }


    @DeleteMapping("/client/{id}")
    public String deleteClient(@PathVariable UUID id, Model model) {
        mediator.handle(new DeleteClientCommand(id));
        return "home";
    }

    @PutMapping("/client/{id}")
    public String editClient(@PathVariable UUID id, @ModelAttribute Client client, Model model) {
        var editedClient = mediator.handle(new EditClientCommand(client));
        model.addAttribute("client", editedClient);
        return "client";
    }
}
