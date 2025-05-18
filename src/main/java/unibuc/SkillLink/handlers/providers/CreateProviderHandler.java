package unibuc.SkillLink.handlers.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.providers.CreateProviderCommand;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.ProvidersRepository;

@Component
public class CreateProviderHandler implements IHandler<CreateProviderCommand, Provider> {

    @Autowired
    private ProvidersRepository providersRepository;

    public Provider handle(CreateProviderCommand createProviderCommand) {
        Provider provider = createProviderCommand.getProvider();
        return providersRepository.save(provider);
    }
}
