package unibuc.SkillLink.handlers.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.abstractions.Mediator;
import unibuc.SkillLink.commands.AreUsersLinkedCommand;
import unibuc.SkillLink.commands.invoices.CreateInvoiceCommand;
import unibuc.SkillLink.exceptions.UsersNotLinkedException;
import unibuc.SkillLink.models.Invoice;
import unibuc.SkillLink.repositories.InvoicesRepository;

@Component
public class CreateInvoiceHandler implements IHandler<CreateInvoiceCommand, Invoice> {
    @Lazy
    @Autowired
    Mediator mediator;

    @Autowired
    private InvoicesRepository invoicesRepository;

    @Override
    public Invoice handle(CreateInvoiceCommand createInvoiceCommand) {
        var linked = mediator.handle(new AreUsersLinkedCommand(createInvoiceCommand.getInvoice().getProvider().getUsername(), createInvoiceCommand.getInvoice().getClient().getUsername()));
        if (!linked)
            throw new UsersNotLinkedException("The users are not linked");
        return invoicesRepository.save(createInvoiceCommand.getInvoice());
    }
}

