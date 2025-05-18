package unibuc.SkillLink.commands.ads;

import lombok.AllArgsConstructor;
import lombok.Data;
import unibuc.SkillLink.DTOs.ads.AdResponse;
import unibuc.SkillLink.DTOs.ads.CreateAdRequest;
import unibuc.SkillLink.abstractions.ICommand;

@Data
@AllArgsConstructor
public class CreateAdCommand implements ICommand<AdResponse> {
    CreateAdRequest request;
    String providerUsername;

}
