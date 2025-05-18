package unibuc.SkillLink.handlers.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.DTOs.booking.BookingResponse;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.bookings.UpdateBookingStatusCommand;
import unibuc.SkillLink.mappers.bookings.BookingMapper;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.BookingsRepository;
import unibuc.SkillLink.repositories.ClientsRepository;
import unibuc.SkillLink.repositories.ProvidersRepository;

import java.util.Optional;

@Component
public class UpdateBookingStatusCommandHandler implements IHandler<UpdateBookingStatusCommand, BookingResponse> {

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private ProvidersRepository providersRepository;

    @Override
    public BookingResponse handle(UpdateBookingStatusCommand command) {
        var booking = bookingsRepository.findById(command.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Optional<Provider> provider = providersRepository.findByUsername(command.getUsername());
        Optional<Client> client = clientsRepository.findByUsername(command.getUsername());

        if (provider.isPresent() && booking.getProvider().equals(provider.get())) {
            booking.setStatus(command.getNewStatus());
            if (command.getConnectionLink() != null) {
                booking.getBookingDetails().setConnectionLink(command.getConnectionLink());
            }
        } else if (client.isPresent() && booking.getClient().equals(client.get())) {
            if (booking.getStatus() != command.getNewStatus()) {
                booking.setStatus(command.getNewStatus());
            } else {
                throw new RuntimeException("Invalid status change for client");
            }
        } else {
            throw new RuntimeException("Unauthorized to update this booking");
        }

        bookingsRepository.save(booking);
        return bookingMapper.fromEntity(booking);
    }
} 