package unibuc.SkillLink.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.GetProvidersCommand;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.ProvidersRepository;

import java.util.List;

@Component
public class GetProvidersHandler implements IHandler<GetProvidersCommand, List<Provider>> {

    @Autowired
    private ProvidersRepository providersRepository;

    @Override
    public List<Provider> handle(GetProvidersCommand getProvidersCommand) {
        return  providersRepository.findAll();
    }
}
