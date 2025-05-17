package unibuc.SkillLink.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.commands.GetCurrentUserCommand;
import unibuc.SkillLink.commands.calendar.CreateCalendarCommand;
import unibuc.SkillLink.models.AppUser;
import unibuc.SkillLink.models.Calendar;
import unibuc.SkillLink.models.Provider;

@Controller
public class CalendarController {
    @Autowired
    private IMediator mediator;

//    @GetMapping("/calendar/edit")
//    public String editCalendar(Model model, Authentication auth) {
//        AppUser currentUser = mediator.handle(new GetCurrentUserCommand(auth));
//
//        if (!(currentUser instanceof Provider provider)) {
//            model.addAttribute("status", 403);
//            model.addAttribute("message", "Only providers can edit their calendar.");
//            return "error";
//        }
//
//        Calendar calendar = mediator.handle(new GetCalendarCommand(provider.getId()));
//        model.addAttribute("calendar", calendar);
//        return "calendar/edit";
//    }
//
    @PostMapping("/calendar")
    public String saveCalendar(@ModelAttribute Calendar calendar, Authentication auth, Model model) {
        AppUser currentUser = mediator.handle(new GetCurrentUserCommand(auth));

        if (!(currentUser instanceof Provider provider) || !calendar.getProvider().getId().equals(provider.getId())) {
            model.addAttribute("status", 403);
            model.addAttribute("message", "Only providers can modify their calendar.");
            return "error";
        }

        calendar.setProvider(provider);
        Calendar updated = mediator.handle(new CreateCalendarCommand(calendar));
        return "redirect:/profile";
    }
}
