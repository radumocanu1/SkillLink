package unibuc.SkillLink.commands.ads;

import lombok.AllArgsConstructor;
import lombok.Data;
import unibuc.SkillLink.DTOs.ads.AdResponse;
import unibuc.SkillLink.abstractions.ICommand;

import java.util.UUID;

@Data
@AllArgsConstructor
public class GetAdCommand implements ICommand<AdResponse> {
    private UUID id;
}
