package unibuc.SkillLink.DTOs.booking;

import lombok.Data;
import unibuc.SkillLink.models.enums.BookingStatus;
import unibuc.SkillLink.models.enums.BookingType;

import java.util.UUID;

@Data
public class BookingResponse {
    UUID id;

    BookingType bookingType;

    BookingDetailsDTO bookingDetails;

    BookingStatus status = BookingStatus.PENDING;

    String clientUsername;

    String providerUsername;

    String clientProfilePicture;

    String providerProfilePicture;
}
