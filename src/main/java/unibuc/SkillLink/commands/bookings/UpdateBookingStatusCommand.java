package unibuc.SkillLink.commands.bookings;

import lombok.AllArgsConstructor;
import lombok.Data;
import unibuc.SkillLink.DTOs.booking.BookingResponse;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.enums.BookingStatus;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UpdateBookingStatusCommand implements ICommand<BookingResponse> {
    private UUID bookingId;
    private BookingStatus newStatus;
    private String username;
    private String connectionLink;

    public UpdateBookingStatusCommand(UUID bookingId, BookingStatus newStatus, String username) {
        this.bookingId = bookingId;
        this.newStatus = newStatus;
        this.username = username;
    }
} 