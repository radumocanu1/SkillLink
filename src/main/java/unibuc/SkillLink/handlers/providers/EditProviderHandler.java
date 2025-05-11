package unibuc.SkillLink.handlers.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.providers.EditProviderCommand;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.ProvidersRepository;

@Component
public class EditProviderHandler implements IHandler<EditProviderCommand, Provider> {
    @Autowired
    private ProvidersRepository providersRepository;

    public Provider handle(EditProviderCommand editProviderCommand) {
        var provider = editProviderCommand.getProvider();
        return providersRepository.save(provider);
    }
}
