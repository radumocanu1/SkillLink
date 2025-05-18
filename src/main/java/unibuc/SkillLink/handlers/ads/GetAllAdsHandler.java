package unibuc.SkillLink.handlers.ads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.DTOs.ads.AdResponse;
import unibuc.SkillLink.DTOs.ads.AdsPage;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.ads.GetAdsPageCommand;
import unibuc.SkillLink.mappers.ads.AdsMapper;
import unibuc.SkillLink.models.Ad;
import unibuc.SkillLink.repositories.AdsRepository;

import java.util.List;

@Component
public class GetAllAdsHandler implements IHandler<GetAdsPageCommand, AdsPage> {
    @Autowired
    AdsMapper adsMapper;

    @Autowired
    AdsRepository adsRepository;

    @Override
    public AdsPage handle(GetAdsPageCommand command) {
        PageRequest pageRequest = PageRequest.of(command.getPage(), command.getSize());
        Page<Ad> pageResult;
        
        if (command.getProviderUsername() != null) {
            pageResult = adsRepository.findByProviderUsername(command.getProviderUsername(), pageRequest);
        } else {
            pageResult = adsRepository.findAll(pageRequest);
        }

        List<AdResponse> responses = pageResult.getContent()
                .stream()
                .map(adsMapper::fromEntity)
                .toList();

        return new AdsPage(
                responses,
                command.getPage(),
                command.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }
}
