package unibuc.SkillLink.handlers;

import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.GetProviderCommand;
import unibuc.SkillLink.commands.GetProvidersCommand;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.ProvidersRepository;

import java.security.NoSuchProviderException;
import java.util.List;

@Component
public class GetProviderHandler implements IHandler<GetProviderCommand, Provider> {

    @Autowired
    private ProvidersRepository providersRepository;

    @Override
    public Provider handle(GetProviderCommand getProviderCommand) {
        var provider = providersRepository.findById(getProviderCommand.getId());
        if (provider.isEmpty()) {
            throw new NotFoundException("Provider", getProviderCommand.getId().toString());
        }
        return provider.get();
    }
}
