package unibuc.SkillLink.handlers.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.DTOs.booking.BookingResponse;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.abstractions.Mediator;
import unibuc.SkillLink.commands.bookings.CreateBookingCommand;
import unibuc.SkillLink.commands.clients.GetClientCommand;
import unibuc.SkillLink.commands.providers.GetProviderCommand;
import unibuc.SkillLink.mappers.bookings.BookingMapper;
import unibuc.SkillLink.repositories.BookingsRepository;

@Component
public class CreateBookingCommandHandler implements IHandler<CreateBookingCommand, BookingResponse> {

    @Autowired
    BookingsRepository bookingsRepository;
    @Autowired
    BookingMapper bookingMapper;
    @Autowired
    @Lazy
    Mediator mediator;

    @Override
    public BookingResponse handle(CreateBookingCommand command) {
        var provider = mediator.handle(new GetProviderCommand(command.getBooking().getProviderUsername()));
        var client = mediator.handle(new GetClientCommand(command.getClientUsername()));
        var booking = bookingMapper.toEntity(command.getBooking());
        booking.setProvider(provider);
        booking.setClient(client);
        bookingsRepository.save(booking);
        return bookingMapper.fromEntity(booking);
    }
}
