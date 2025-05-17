package unibuc.SkillLink.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import unibuc.SkillLink.models.AvailabilitySlot;
import unibuc.SkillLink.models.Calendar;
import unibuc.SkillLink.repositories.CalendarsRepository;

import java.util.UUID;

@Controller
@RequestMapping("/slots")
public class AvailabilitySlotController {

    @Autowired
    private AvailabilitySlotRepository slotRepo;

    @Autowired
    private CalendarsRepository calendarsRepo;

    @PostMapping("/create")
    public String createSlot(@ModelAttribute AvailabilitySlot slot, @RequestParam UUID calendarId) {
        Calendar calendar = calendarsRepo.findById(calendarId).orElseThrow();
        slot.setCalendar(calendar);
        slotRepo.save(slot);
        return "redirect:/profile";
    }

    @PostMapping("/delete/{id}")
    public String deleteSlot(@PathVariable UUID id) {
        slotRepo.deleteById(id);
        return "redirect:/profile";
    }

    @PostMapping("/edit/{id}")
    public String editSlot(@PathVariable UUID id, @ModelAttribute AvailabilitySlot updatedSlot) {
        AvailabilitySlot existing = slotRepo.findById(id).orElseThrow();
        existing.setDate(updatedSlot.getDate());
        existing.setStartTime(updatedSlot.getStartTime());
        existing.setEndTime(updatedSlot.getEndTime());
        slotRepo.save(existing);
        return "redirect:/profile";
    }
}

