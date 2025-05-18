package unibuc.SkillLink.handlers.ads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.DTOs.ads.AdResponse;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.ads.GetAdCommand;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.mappers.ads.AdsMapper;
import unibuc.SkillLink.repositories.AdsRepository;

@Component
public class GetAdCommandHandler implements IHandler<GetAdCommand, AdResponse> {
    @Autowired
    AdsMapper adsMapper;

    @Autowired
    AdsRepository adsRepository;

    @Override
    public AdResponse handle(GetAdCommand command) {
        var ad = adsRepository.findById(command.getId())
                .orElseThrow(() -> new NotFoundException("Ad",command.getId().toString()));
        var adResponse = adsMapper.fromEntity(ad);
        adResponse.setProviderUsername(ad.getProvider().getUsername());
        adResponse.setProviderPicture(ad.getProvider().getProfilePicture());
        return adResponse;
    }
}
