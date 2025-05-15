package unibuc.SkillLink.handlers.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.clients.GetClientCommand;
import unibuc.SkillLink.commands.invoices.GetInvoiceCommand;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Invoice;
import unibuc.SkillLink.repositories.ClientsRepository;
import unibuc.SkillLink.repositories.InvoicesRepository;

import java.util.Optional;

@Component
public class GetInvoiceHandler implements IHandler<GetInvoiceCommand, Invoice> {
    @Autowired
    private InvoicesRepository invoicesRepository;

    @Override
    public Invoice handle(GetInvoiceCommand getInvoiceCommand) {
        Optional<Invoice> invoice;

        invoice = invoicesRepository.findById(getInvoiceCommand.getId());

        if (invoice.isEmpty()) {
            throw new NotFoundException(
                    "Invoice",
                    getInvoiceCommand.getId().toString());
        }
        return invoice.get();
    }
}
