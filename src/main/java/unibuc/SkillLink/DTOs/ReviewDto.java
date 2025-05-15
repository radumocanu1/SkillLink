package unibuc.SkillLink.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import unibuc.SkillLink.models.Review;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private UUID id;
    private String content;
    private Integer stars;
    private String clientUsername;
    private String providerUsername;

    public ReviewDto(Review review) {
        this.content = review.getContent();
        this.stars = review.getStars();
        this.clientUsername = review.getClient().getUsername();
    }

    public ReviewDto(String content, int stars, String clientUsername, UUID id) {
        this.id = id;
        this.content = content;
        this.stars = stars;
        this.clientUsername = clientUsername;
    }
}
