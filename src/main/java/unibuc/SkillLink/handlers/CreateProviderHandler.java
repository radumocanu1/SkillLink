package unibuc.SkillLink.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.CreateProviderCommand;
import unibuc.SkillLink.commands.GetProviderCommand;
import unibuc.SkillLink.commands.GetProvidersCommand;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.ProvidersRepository;

import java.util.List;

@Component
public class CreateProviderHandler implements IHandler<CreateProviderCommand, Provider> {

    @Autowired
    private ProvidersRepository providersRepository;

    public Provider handle(CreateProviderCommand createProviderCommand) {
        return providersRepository.save(createProviderCommand.getProvider());
    }
}
