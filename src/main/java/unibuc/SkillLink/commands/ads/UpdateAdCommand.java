package unibuc.SkillLink.commands.ads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.DTOs.ads.CreateAdRequest;
import unibuc.SkillLink.DTOs.ads.AdResponse;
import unibuc.SkillLink.abstractions.ICommand;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class UpdateAdCommand implements ICommand<AdResponse> {
    private UUID id;
    private String providerUsername;
    private CreateAdRequest request;
} 