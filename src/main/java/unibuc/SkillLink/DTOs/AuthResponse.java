package unibuc.SkillLink.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthResponse {
    boolean authenticated;
    String authority;
}
