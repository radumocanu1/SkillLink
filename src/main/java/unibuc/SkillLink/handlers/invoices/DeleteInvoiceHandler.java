package unibuc.SkillLink.handlers.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.invoices.DeleteInvoiceCommand;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.repositories.InvoicesRepository;

@Component
public class DeleteInvoiceHandler implements IHandler<DeleteInvoiceCommand, Void> {
    @Autowired
    private InvoicesRepository invoicesRepository;

    public Void handle(DeleteInvoiceCommand cmd) {
        var id = cmd.getId();
        if (!invoicesRepository.existsById(id)) {
            throw new NotFoundException("Invoice",id.toString());
        }

        invoicesRepository.deleteById(id);
        return null;
    }
}
