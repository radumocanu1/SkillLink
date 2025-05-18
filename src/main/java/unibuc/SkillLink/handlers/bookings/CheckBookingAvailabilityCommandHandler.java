package unibuc.SkillLink.handlers.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.abstractions.IHandler;
import unibuc.SkillLink.commands.bookings.CheckBookingAvailabilityCommand;
import unibuc.SkillLink.models.Booking;
import unibuc.SkillLink.models.enums.BookingStatus;
import unibuc.SkillLink.repositories.BookingsRepository;

import java.time.LocalTime;
import java.util.List;

@Component
public class CheckBookingAvailabilityCommandHandler implements IHandler<CheckBookingAvailabilityCommand, Boolean> {

    @Autowired
    private BookingsRepository bookingsRepository;

    @Override
    public Boolean handle(CheckBookingAvailabilityCommand command) {
        List<Booking> existingBookings = bookingsRepository.findByProviderUsernameAndBookingDetailsDateAndStatus(
            command.getProviderUsername(),
            command.getDate(),
            BookingStatus.ACCEPTED
        );

        for (Booking booking : existingBookings) {
            LocalTime existingStart = booking.getBookingDetails().getStartTime();
            LocalTime existingEnd = booking.getBookingDetails().getEndTime();
            
            if (!(command.getEndTime().isBefore(existingStart) || command.getStartTime().isAfter(existingEnd))) {
                return false;
            }
        }

        return true;
    }
} 