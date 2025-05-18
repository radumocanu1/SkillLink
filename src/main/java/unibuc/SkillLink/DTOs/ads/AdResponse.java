package unibuc.SkillLink.DTOs.ads;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AdResponse {

    String title;

    private String providerPicture;

    private String providerUsername;

    int rate;

    UUID id;

    String description;

    String picture;
}
