package unibuc.SkillLink.DTOs.providers;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRateRequest {
    @DecimalMin(value = "0.0", message = "Rate must be at least 0.0")
    private double rate;
} 