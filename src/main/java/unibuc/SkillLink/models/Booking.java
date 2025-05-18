package unibuc.SkillLink.models;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import unibuc.SkillLink.models.enums.BookingStatus;
import unibuc.SkillLink.models.enums.BookingType;

import java.util.UUID;

@Entity
public class Booking extends BaseModel<UUID> {
    @Getter
    @Setter
    BookingType bookingType;

    @Embedded
    @Getter
    @Setter
    BookingDetails bookingDetails;

    @Getter
    @Setter
    BookingStatus status = BookingStatus.PENDING;

    @ManyToOne
    @Getter
    @Setter
    Client client;

    @ManyToOne
    @Getter
    @Setter
    Provider provider;

}
