package unibuc.SkillLink.handlers.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.providers.GetProviderCommand;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.ProvidersRepository;

import java.util.Optional;

@Component
public class GetProviderHandler implements IHandler<GetProviderCommand, Provider> {

    @Autowired
    private ProvidersRepository providersRepository;

    @Override
    public Provider handle(GetProviderCommand getProviderCommand) {
        Optional<Provider> provider;
        if (getProviderCommand.getUsername() != null)
            provider = providersRepository.findByUsername(getProviderCommand.getUsername());
        else
            provider = providersRepository.findById(getProviderCommand.getId());

        if (provider.isEmpty()) {
            throw new NotFoundException(
                    "Provider",
                    getProviderCommand.getId() == null ?
                            getProviderCommand.getUsername() :
                            getProviderCommand.getId().toString());
        }
        return provider.get();
    }
}
