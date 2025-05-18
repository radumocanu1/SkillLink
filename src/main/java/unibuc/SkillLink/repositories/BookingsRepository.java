package unibuc.SkillLink.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unibuc.SkillLink.models.Booking;
import unibuc.SkillLink.models.BookingDetails;
import unibuc.SkillLink.models.enums.BookingStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingsRepository extends CrudRepository<Booking, UUID> {
    List<Booking> findByProviderUsernameAndBookingDetailsDateAndStatus(String providerUsername, LocalDate date, BookingStatus status);
}
