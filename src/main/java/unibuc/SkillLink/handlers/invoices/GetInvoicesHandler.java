package unibuc.SkillLink.handlers.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.invoices.GetInvoicesCommand;
import unibuc.SkillLink.commands.providers.GetClientsCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Invoice;
import unibuc.SkillLink.repositories.ClientsRepository;
import unibuc.SkillLink.repositories.InvoicesRepository;

import java.util.List;
import java.util.UUID;

@Component
public class GetInvoicesHandler implements IHandler<GetInvoicesCommand, List<Invoice>> {
    @Autowired
    private InvoicesRepository invoiceRepository;

    @Override
    public List<Invoice> handle(GetInvoicesCommand command) {
        UUID clientId = command.getClientId();
        UUID providerId = command.getProviderId();
        Sort sorting = command.getSort();

        if (clientId != null && providerId != null) {
            return invoiceRepository.findByClientIdAndProviderId(clientId, providerId,sorting);
        } else if (clientId != null) {
            return invoiceRepository.findByClientId(clientId,sorting);
        } else if (providerId != null) {
            return invoiceRepository.findByProviderId(providerId,sorting);
        } else {
            return (List<Invoice>) invoiceRepository.findAll();
        }
    }

}
