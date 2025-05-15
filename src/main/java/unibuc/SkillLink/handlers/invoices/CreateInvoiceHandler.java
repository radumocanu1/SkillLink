package unibuc.SkillLink.handlers.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.invoices.CreateInvoiceCommand;
import unibuc.SkillLink.commands.providers.CreateProviderCommand;
import unibuc.SkillLink.models.Invoice;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.InvoicesRepository;
import unibuc.SkillLink.repositories.ProvidersRepository;

@Component
public class CreateInvoiceHandler implements IHandler<CreateInvoiceCommand, Invoice> {
    @Autowired
    private InvoicesRepository invoicesRepository;

    @Override
    public Invoice handle(CreateInvoiceCommand CreateInvoiceCommand) {
        return invoicesRepository.save(CreateInvoiceCommand.getInvoice());
    }
}

