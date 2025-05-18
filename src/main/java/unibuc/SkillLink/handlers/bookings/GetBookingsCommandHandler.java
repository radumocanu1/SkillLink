package unibuc.SkillLink.handlers.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.DTOs.booking.BookingResponse;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.bookings.GetBookingsCommand;
import unibuc.SkillLink.mappers.bookings.BookingMapper;

import java.util.Set;

@Component
public class GetBookingsCommandHandler implements IHandler<GetBookingsCommand, Set<BookingResponse>> {


    @Autowired
    BookingMapper bookingMapper;

    @Override
    public Set<BookingResponse> handle(GetBookingsCommand command) {
        return command.getAppUser().getBookings()
                .stream()
                .map(booking -> bookingMapper.fromEntity(booking))
                .collect(java.util.stream.Collectors.toSet());

    }
}
