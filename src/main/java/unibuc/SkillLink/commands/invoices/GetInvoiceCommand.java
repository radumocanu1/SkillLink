package unibuc.SkillLink.commands.invoices;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Invoice;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class GetInvoiceCommand implements ICommand<Invoice> {
    UUID id;
}
