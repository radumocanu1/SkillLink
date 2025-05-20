package unibuc.SkillLink.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupForm {

    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username must be at most 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Username must not contain special characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z]).+$",
            message = "Password must contain at least one lowercase and one uppercase letter"
    )
    private String password;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must be at most 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "First name must not contain special characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must be at most 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Last name must not contain special characters")
    private String lastName;

    private String userType;

}
