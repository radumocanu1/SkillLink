package unibuc.SkillLink.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unibuc.SkillLink.models.Review;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ReviewsRepository extends CrudRepository<Review, UUID> {

    Set<Review> findAllByProviderId(UUID providerId);

}
