package unibuc.SkillLink.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unibuc.SkillLink.models.AvailabilitySlot;

import java.util.UUID;
@Repository

public interface AvailabilitySlotsRepository extends CrudRepository<AvailabilitySlot, UUID> {
}
