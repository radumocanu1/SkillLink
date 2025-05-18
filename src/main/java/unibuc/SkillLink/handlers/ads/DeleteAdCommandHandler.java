package unibuc.SkillLink.handlers.ads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.ads.DeleteAdCommand;
import unibuc.SkillLink.exceptions.ForbiddenException;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.repositories.AdsRepository;
import unibuc.SkillLink.services.AdPictureService;

@Component
public class DeleteAdCommandHandler implements IHandler<DeleteAdCommand, Void> {
    @Autowired
    private AdsRepository adsRepository;
    
    @Autowired
    private AdPictureService adPictureService;

    @Override
    public Void handle(DeleteAdCommand command) {
        var ad = adsRepository.findById(command.getId())
                .orElseThrow(() -> new NotFoundException("Ad", command.getId().toString()));

        if (!ad.getProvider().getUsername().equals(command.getProviderUsername())) {
            throw new ForbiddenException();
        }

        if (ad.getPicture() != null) {
            adPictureService.deleteAdPicture(ad.getPicture());
        }

        adsRepository.delete(ad);
        return null;
    }
} 