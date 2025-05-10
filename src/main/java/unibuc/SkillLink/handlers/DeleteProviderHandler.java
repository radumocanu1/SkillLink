package unibuc.SkillLink.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.CreateProviderCommand;
import unibuc.SkillLink.commands.DeleteProviderCommand;
import unibuc.SkillLink.commands.GetProviderCommand;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.ProvidersRepository;

import java.util.UUID;

@Component
public class DeleteProviderHandler implements IHandler<DeleteProviderCommand, Void> {
    @Autowired
    private ProvidersRepository providersRepository;

    public Void handle(DeleteProviderCommand deleteProviderCommand) {
        var id = deleteProviderCommand.getId();
        if (!providersRepository.existsById(id)) {
            throw new NotFoundException("Provider",id.toString());
        }

        providersRepository.deleteById(id);
        return null;
    }
}
