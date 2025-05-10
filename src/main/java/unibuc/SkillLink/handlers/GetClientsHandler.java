package unibuc.SkillLink.handlers;

import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.GetClientsCommand;
import unibuc.SkillLink.commands.GetProvidersCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.ClientsRepository;
import unibuc.SkillLink.repositories.ProvidersRepository;

import java.util.List;

@Component
public class GetClientsHandler implements IHandler<GetClientsCommand, List<Client>> {
    @Autowired
    private ClientsRepository clientsRepository;

    @Override
    public List<Client> handle(GetClientsCommand getClientsCommand) {
        return clientsRepository.findAll();
    }
}
