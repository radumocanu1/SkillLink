package unibuc.SkillLink.commands.ads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.DTOs.ads.AdsPage;
import unibuc.SkillLink.abstractions.ICommand;

@AllArgsConstructor
@Getter
public class GetAdsPageCommand implements ICommand<AdsPage> {
    private int page;
    private int size;
    private String providerUsername;

    public GetAdsPageCommand(int page, int size) {
        this.page = page;
        this.size = size;
        this.providerUsername = null;
    }
}
