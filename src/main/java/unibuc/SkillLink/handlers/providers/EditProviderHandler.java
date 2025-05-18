package unibuc.SkillLink.handlers.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.providers.EditProviderCommand;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.ProvidersRepository;

import java.util.NoSuchElementException;

@Component
public class EditProviderHandler implements IHandler<EditProviderCommand, Provider> {
    @Autowired
    private ProvidersRepository providersRepository;

    public Provider handle(EditProviderCommand editProviderCommand) {
        var sourceProvider = editProviderCommand.getProvider();

        var existingProvider = providersRepository.findById(sourceProvider.getId())
                .orElseThrow(() -> new NoSuchElementException("Provider not found with ID: " + sourceProvider.getId()));

        if (hasText(sourceProvider.getFirstName())) {
            existingProvider.setFirstName(sourceProvider.getFirstName());
        }

        if (hasText(sourceProvider.getLastName())) {
            existingProvider.setLastName(sourceProvider.getLastName());
        }

        if (hasText(sourceProvider.getUsername())) {
            existingProvider.setUsername(sourceProvider.getUsername());
        }

        if (sourceProvider.getProfilePicture() != null) {
            existingProvider.setProfilePicture(sourceProvider.getProfilePicture());
        }

        if (sourceProvider.getRate() != existingProvider.getRate()) {
            existingProvider.setRate(sourceProvider.getRate());
        }

        return providersRepository.save(existingProvider);
    }

    private boolean hasText(String str) {
        return StringUtils.hasText(str);
    }
}
