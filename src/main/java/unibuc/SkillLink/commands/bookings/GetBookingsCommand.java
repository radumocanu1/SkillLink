package unibuc.SkillLink.commands.bookings;

import lombok.AllArgsConstructor;
import lombok.Data;
import unibuc.SkillLink.DTOs.booking.BookingResponse;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.AppUser;

import java.util.Set;

@Data
@AllArgsConstructor
public class GetBookingsCommand implements ICommand<Set<BookingResponse>> {
    AppUser appUser;
}
