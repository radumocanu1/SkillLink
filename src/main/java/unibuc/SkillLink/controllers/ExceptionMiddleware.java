package unibuc.SkillLink.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import unibuc.SkillLink.exceptions.NotFoundException;

@ControllerAdvice
public class ExceptionMiddleware {
    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException ex, Model model) {
        model.addAttribute("entityName", ex.getEntityName());
        model.addAttribute("id", ex.getId());
        return "notFound";
    }
}
