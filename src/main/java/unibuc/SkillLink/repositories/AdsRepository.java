package unibuc.SkillLink.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import unibuc.SkillLink.models.Ad;

import java.util.UUID;

@Repository
public interface AdsRepository extends PagingAndSortingRepository<Ad, UUID>, CrudRepository<Ad, UUID> {
    Page<Ad> findByProviderUsername(String username, Pageable pageable);
}
