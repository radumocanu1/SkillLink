package unibuc.SkillLink.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import unibuc.SkillLink.annotations.Authorized;

@Aspect
@Component
public class AuthorizationAspect {

    @Around("@annotation(authorized)")
    public Object checkAuthorization(ProceedingJoinPoint joinPoint, Authorized authorized) throws Throwable {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return handleUnauthorized();
        }

        if (authorized.authority().isEmpty()) {
            return joinPoint.proceed();
        }

        String requiredRole = "ROLE_" + authorized.authority().toUpperCase();

        boolean hasAuthority = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals(requiredRole));

        if (!hasAuthority) {
            return "unauthorized";
        }

        return joinPoint.proceed();
    }

    public ModelAndView handleUnauthorized() {
        ModelAndView mav = new ModelAndView("unauthorized");
        mav.addObject("errorMessage", "You do not have the required permissions to access this page. Please contact the administrator if you believe this is an error. Thank you for your understanding and cooperation.");
        return mav;
    }
}
