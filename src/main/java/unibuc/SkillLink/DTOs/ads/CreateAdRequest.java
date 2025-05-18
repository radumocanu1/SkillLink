package unibuc.SkillLink.DTOs.ads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAdRequest{

    @Min(value = 0, message = "Rate must be a positive number")
    @Max(value = 1000, message = "Rate cannot be greater than 1000")
    int rate;

    @Size(min = 50, max = 5000, message = "Description must be between 50 and 5000 characters")
    String description;

    String picture;

    @Size(min = 10, max = 100, message = "Title must be between 10 and 100 characters")
    String title;
}
