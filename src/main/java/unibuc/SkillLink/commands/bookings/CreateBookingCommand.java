package unibuc.SkillLink.commands.bookings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.DTOs.booking.BookingResponse;
import unibuc.SkillLink.DTOs.booking.CreateBookingRequest;
import unibuc.SkillLink.abstractions.ICommand;

@Getter
@AllArgsConstructor
public class CreateBookingCommand implements ICommand<BookingResponse> {

    CreateBookingRequest booking;
    String clientUsername;
}
