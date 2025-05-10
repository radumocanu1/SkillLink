package unibuc.SkillLink.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.CreateProviderCommand;
import unibuc.SkillLink.commands.DeleteProviderCommand;
import unibuc.SkillLink.commands.EditProviderCommand;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.ProvidersRepository;

@Component
public class EditProviderHandler implements IHandler<EditProviderCommand, Provider> {
    @Autowired
    private ProvidersRepository providersRepository;

    public Provider handle(EditProviderCommand editProviderCommand) {
        var provider = editProviderCommand.getProvider();
//        if (existingProvider.isEmpty()) {
//            throw new NotFoundException("Provider", provider.toString());
//        }
//        var newData = editProviderCommand.getProvider();
//
//        existingProvider.setFirstName(newData.getFirstName());
//        existingProvider.setLastName(newData.getLastName());

        return providersRepository.save(provider);
    }
}
