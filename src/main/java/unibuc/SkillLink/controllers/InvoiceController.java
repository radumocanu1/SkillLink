package unibuc.SkillLink.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.annotations.SetRoles;
import unibuc.SkillLink.commands.GetCurrentUserCommand;
import unibuc.SkillLink.commands.clients.CreateClientCommand;
import unibuc.SkillLink.commands.clients.GetClientCommand;
import unibuc.SkillLink.commands.invoices.CreateInvoiceCommand;
import unibuc.SkillLink.commands.invoices.DeleteInvoiceCommand;
import unibuc.SkillLink.commands.invoices.GetInvoiceCommand;
import unibuc.SkillLink.commands.invoices.GetInvoicesCommand;
import unibuc.SkillLink.commands.providers.DeleteProviderCommand;
import unibuc.SkillLink.commands.providers.GetProviderCommand;
import org.springframework.data.domain.Sort;
import unibuc.SkillLink.models.AppUser;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Invoice;
import unibuc.SkillLink.models.Provider;

import java.time.LocalDate;
import java.util.UUID;

@Controller
public class InvoiceController {
    @Autowired
    IMediator mediator;

    @GetMapping("/invoice/create")
    public String CreateInvoice(@RequestParam("providerId") UUID providerId,
                                   @RequestParam("clientId") UUID clientId,
                                   Model model) {
        Provider provider = mediator.handle(new GetProviderCommand(providerId));
        Client client = mediator.handle(new GetClientCommand(clientId));

        Invoice invoice = new Invoice();
        invoice.setProvider(provider);
        invoice.setClient(client);
        invoice.setDateCreated(LocalDate.now());

        model.addAttribute("invoice", invoice);
        return "invoice/create";
    }

    @GetMapping("/invoice/{id}")
    public String getInvoice(@PathVariable UUID id, Model model, Authentication authentication) {
        var invoice = mediator.handle(new GetInvoiceCommand(id));
        model.addAttribute("invoice", invoice);
        return "invoice/show";
    }

    @GetMapping("/invoices")
    @SetRoles
    public String getAllInvoices(@RequestParam(required = false) UUID clientId,
                                 @RequestParam(required = false) UUID providerId,
                                 @RequestParam(defaultValue = "dateCreated") String sort,
                                 @RequestParam(defaultValue = "desc") String direction,
                                 Model model, Authentication authentication) {
        AppUser currentUser = mediator.handle(new GetCurrentUserCommand(authentication));
        
        if (currentUser instanceof Provider provider) {
            providerId = provider.getId();
        } else if (currentUser instanceof Client client) {
            clientId = client.getId();
        }
        Sort sorting = Sort.by(Sort.Direction.fromString(direction), sort);
        var invoices = mediator.handle(new GetInvoicesCommand(clientId, providerId, sorting));
        model.addAttribute("invoices", invoices);
        model.addAttribute("authentication", authentication);
        return "invoice/list";
    }

    @PostMapping("/invoice/create")
    public String createInvoice(@ModelAttribute("invoice") @Valid Invoice invoice, BindingResult bindingResult,Model model,  Authentication authentication) {
        Provider provider = mediator.handle(new GetProviderCommand(invoice.getProvider().getId()));
        Client client = mediator.handle(new GetClientCommand(invoice.getClient().getId()));

        invoice.setProvider(provider);
        invoice.setClient(client);

        if (bindingResult.hasErrors()) {
            model.addAttribute("invoice", invoice);
            return "invoice/create";
        }

        if (invoice.getDateCreated() == null) {
            invoice.setDateCreated(LocalDate.now());
        }

        var newInvoice = mediator.handle(new CreateInvoiceCommand(invoice));
        model.addAttribute("invoice", newInvoice);
        return "redirect:/invoices";
    }

    @DeleteMapping("/invoice/{id}")
    public String deleteInvoice(@PathVariable UUID id, Model model, Authentication auth) {
        AppUser currentUser = mediator.handle(new GetCurrentUserCommand(auth));

        Invoice invoice = mediator.handle(new GetInvoiceCommand(id));

        if (!(currentUser instanceof Provider typeProvider) || !invoice.getProvider().getId().equals(typeProvider.getId())) {
            model.addAttribute("status", 403);
            model.addAttribute("message", "Operation not permitted");
            return "error";
        }
        mediator.handle(new DeleteInvoiceCommand(id));

        return "redirect:/invoices?providerId=" + typeProvider.getId();
    }
}
