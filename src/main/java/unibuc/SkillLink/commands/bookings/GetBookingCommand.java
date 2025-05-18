package unibuc.SkillLink.commands.bookings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.DTOs.booking.BookingResponse;
import unibuc.SkillLink.abstractions.ICommand;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class GetBookingCommand implements ICommand<BookingResponse> {
    UUID id;
} 