package unibuc.SkillLink.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unibuc.SkillLink.models.Calendar;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CalendarsRepository extends CrudRepository<Calendar, UUID> {
    Optional<Calendar> findByProviderId(UUID providerId);
}
