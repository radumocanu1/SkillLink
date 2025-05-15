package unibuc.SkillLink.commands.invoices;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Invoice;

@Getter
@AllArgsConstructor
public class CreateInvoiceCommand implements ICommand<Invoice> {
    Invoice invoice;
}
