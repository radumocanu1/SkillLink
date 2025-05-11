package unibuc.SkillLink.aspects;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import unibuc.SkillLink.annotations.SetRoles;

import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class RoleAspect {

    @Before(value = "@annotation(setRoles) && args(.., model, authentication)", argNames = "setRoles,model,authentication")
    public void addRoleToModel(SetRoles setRoles, Model model, Authentication authentication) {
        if (authentication != null) {
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            model.addAttribute(setRoles.value(), roles);
        }
    }
}