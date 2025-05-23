package unibuc.SkillLink.handlers.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.clients.DeleteClientCommand;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.repositories.ClientsRepository;

@Component
public class DeleteClientHandler implements IHandler<DeleteClientCommand, Void> {
    @Autowired
    private ClientsRepository clientsRepository;

    public Void handle(DeleteClientCommand deleteProviderCommand) {
        var id = deleteProviderCommand.getId();
        if (!clientsRepository.existsById(id)) {
            throw new NotFoundException("Client",id.toString());
        }

        clientsRepository.deleteById(id);
        return null;
    }
}
