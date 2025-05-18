package unibuc.SkillLink.handlers.ads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.DTOs.ads.AdResponse;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.abstractions.Mediator;
import unibuc.SkillLink.commands.ads.CreateAdCommand;
import unibuc.SkillLink.commands.providers.GetProviderCommand;
import unibuc.SkillLink.mappers.ads.AdsMapper;
import unibuc.SkillLink.repositories.AdsRepository;
import unibuc.SkillLink.services.AdPictureService;

@Component
public class CreateAdCommandHandler implements IHandler<CreateAdCommand, AdResponse> {
    @Autowired
    AdsMapper adsMapper;

    @Autowired
    AdsRepository adsRepository;

    @Autowired
    AdPictureService adPictureService;

    @Lazy
    @Autowired
    Mediator mediator;

    @Override
    public AdResponse handle(CreateAdCommand command) {
        var provider = mediator.handle(new GetProviderCommand(command.getProviderUsername()));
        var ad = adsMapper.toEntity(command.getRequest());
        ad.setProvider(provider);
        
        var savedAd = adsRepository.save(ad);
        
        if (command.getRequest().getPicture() != null && !command.getRequest().getPicture().isEmpty()) {
            String filename = adPictureService.saveAdPicture(command.getRequest().getPicture(), savedAd.getId());
            savedAd.setPicture(filename);
            savedAd = adsRepository.save(savedAd);
        }
        
        return adsMapper.fromEntity(savedAd);
    }
}
