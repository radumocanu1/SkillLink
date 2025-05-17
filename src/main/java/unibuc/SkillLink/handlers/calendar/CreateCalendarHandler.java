package unibuc.SkillLink.handlers.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.calendar.CreateCalendarCommand;
import unibuc.SkillLink.commands.reviews.CreateReviewCommand;
import unibuc.SkillLink.models.AvailabilitySlot;
import unibuc.SkillLink.models.Calendar;
import unibuc.SkillLink.models.Review;
import unibuc.SkillLink.repositories.CalendarsRepository;
import unibuc.SkillLink.repositories.ProvidersRepository;
import unibuc.SkillLink.repositories.ReviewsRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Component
public class CreateCalendarHandler implements IHandler<CreateCalendarCommand, Calendar> {
    @Autowired
    private CalendarsRepository calendarsRepository;

    @Autowired
    private ProvidersRepository providerRepository; // dacă ai

    @Override
    public Calendar handle(CreateCalendarCommand cmd) {
        Calendar inputCalendar = cmd.getCalendar();
        UUID providerId = inputCalendar.getProvider().getId();

        // Caută calendarul existent după provider
        Optional<Calendar> optionalCalendar = calendarsRepository.findByProviderId(providerId);

        Calendar calendarToSave;
        if (optionalCalendar.isPresent()) {
            calendarToSave = optionalCalendar.get();

            if (inputCalendar.getSlots() != null) {
                if (calendarToSave.getSlots() == null) {
                    calendarToSave.setSlots(new ArrayList<>());
                }
                for (AvailabilitySlot newSlot : inputCalendar.getSlots()) {
                    newSlot.setCalendar(calendarToSave);
                    calendarToSave.getSlots().add(newSlot);
                }
            }
        } else {
            calendarToSave = inputCalendar;

            if (calendarToSave.getSlots() != null) {
                for (AvailabilitySlot slot : calendarToSave.getSlots()) {
                    slot.setCalendar(calendarToSave);
                }
            }
        }

        return calendarsRepository.save(calendarToSave);
    }
}
