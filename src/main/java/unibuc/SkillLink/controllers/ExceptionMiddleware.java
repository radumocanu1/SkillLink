package unibuc.SkillLink.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import unibuc.SkillLink.exceptions.ForbiddenException;
import unibuc.SkillLink.exceptions.NotFoundException;
import unibuc.SkillLink.exceptions.UsersNotLinkedException;

@ControllerAdvice
public class ExceptionMiddleware {
    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException ex, Model model) {
        model.addAttribute("entityName", ex.getEntityName());
        model.addAttribute("id", ex.getId());
        return "notFound";
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(UsersNotLinkedException.class)
    public String handleUsersNotLinkedException(UsersNotLinkedException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        return "error";
    }
    @ResponseStatus(HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler(ForbiddenException.class)
    public String handleForbiddenException() {
        return "unauthorized";
    }

}
