package unibuc.SkillLink.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import unibuc.SkillLink.commands.providers.GetProvidersCommand;
import unibuc.SkillLink.models.AppUser;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Invoice;
import unibuc.SkillLink.models.Provider;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Controller
public class InvoiceController {
    @Autowired
    IMediator mediator;

    @GetMapping("/invoice/create")
    public String getCreateInvoice(@RequestParam("providerId") UUID providerId,
                                   @RequestParam("clientId") UUID clientId,
                                   Model model) {
        Provider provider = mediator.handle(new GetProviderCommand(providerId));
        Client client = mediator.handle(new GetClientCommand(clientId));

        Invoice invoice = new Invoice();
        invoice.setProvider(provider);
        invoice.setClient(client);

        model.addAttribute("invoice", invoice);
        return "invoice/create";
    }

    @GetMapping("/invoice/{id}")
    public String getInvoice(@PathVariable UUID id, Model model, Authentication authentication) {
        var invoice = mediator.handle(new GetInvoiceCommand(id));
        model.addAttribute("invoice", invoice);
        return "invoice/show";
    }

    @PostMapping("/invoice/{id}")
    public String editInvoice(@ModelAttribute Invoice invoice, Model model) {
        var createdInvoice = mediator.handle(new CreateInvoiceCommand(invoice));
        model.addAttribute("invoice", createdInvoice);
        return "invoice/show";
    }

    @GetMapping("/invoices")
    @SetRoles
    public String getAllInvoices(@RequestParam(required = false) UUID clientId,
                                 @RequestParam(required = false) UUID providerId,
                                 Model model, Authentication authentication) {
        var invoices = mediator.handle(new GetInvoicesCommand(clientId, providerId));
        model.addAttribute("invoices", invoices);
        model.addAttribute("authentication", authentication);
        return "invoice/list";
    }

    @PostMapping("/invoice/create")
    public String createInvoice(@ModelAttribute Invoice invoice, Model model, Authentication authentication) {
        invoice.setDateCreated(LocalDate.now());
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
