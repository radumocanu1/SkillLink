package unibuc.SkillLink.handlers.ads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.DTOs.ads.AdResponse;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.ads.UpdateAdCommand;
import unibuc.SkillLink.exceptions.ForbiddenException;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.mappers.ads.AdsMapper;
import unibuc.SkillLink.repositories.AdsRepository;
import unibuc.SkillLink.services.AdPictureService;

@Component
public class UpdateAdCommandHandler implements IHandler<UpdateAdCommand, AdResponse> {
    @Autowired
    private AdsRepository adsRepository;
    
    @Autowired
    private AdPictureService adPictureService;
    
    @Autowired
    private AdsMapper adsMapper;

    @Override
    public AdResponse handle(UpdateAdCommand command) {
        var ad = adsRepository.findById(command.getId())
                .orElseThrow(() -> new NotFoundException("Ad", command.getId().toString()));

        if (!ad.getProvider().getUsername().equals(command.getProviderUsername())) {
            throw new ForbiddenException();
        }

        ad.setTitle(command.getRequest().getTitle());
        ad.setDescription(command.getRequest().getDescription());
        ad.setRate(command.getRequest().getRate());

        if (command.getRequest().getPicture() != null && !command.getRequest().getPicture().isEmpty()) {
            if (ad.getPicture() != null) {
                adPictureService.deleteAdPicture(ad.getPicture());
            }
            String filename = adPictureService.saveAdPicture(command.getRequest().getPicture(), ad.getId());
            ad.setPicture(filename);
        }

        var savedAd = adsRepository.save(ad);
        return adsMapper.fromEntity(savedAd);
    }
} 