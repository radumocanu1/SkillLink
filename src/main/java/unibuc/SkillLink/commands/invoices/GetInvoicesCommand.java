package unibuc.SkillLink.commands.invoices;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Invoice;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class GetInvoicesCommand implements ICommand<List<Invoice>> {
    UUID clientId;
    UUID  providerId;
    Sort sort;

}
