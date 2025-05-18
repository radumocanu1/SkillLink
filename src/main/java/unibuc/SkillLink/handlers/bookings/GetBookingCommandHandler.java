package unibuc.SkillLink.handlers.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.DTOs.booking.BookingResponse;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.bookings.GetBookingCommand;
import unibuc.SkillLink.mappers.bookings.BookingMapper;
import unibuc.SkillLink.repositories.BookingsRepository;

@Component
public class GetBookingCommandHandler implements IHandler<GetBookingCommand, BookingResponse> {

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private BookingMapper bookingMapper;

    @Override
    public BookingResponse handle(GetBookingCommand command) {
        var booking = bookingsRepository.findById(command.getId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return bookingMapper.fromEntity(booking);
    }
} 