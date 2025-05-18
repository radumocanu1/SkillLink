package unibuc.SkillLink.DTOs.booking;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

public class AcceptBookingRequest {
    //TODO
    @DecimalMin(value = "0.0", message = "Rate must be at least 0.0")
    @DecimalMax(value = "1.0", message = "Rate must be at most 1.0")
    private double ratePercent;
}
