package unibuc.SkillLink.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unibuc.SkillLink.models.Invoice;
import unibuc.SkillLink.models.Provider;

import java.util.List;
import java.util.UUID;
@Repository
public interface InvoicesRepository extends CrudRepository<Invoice, UUID>, JpaRepository<Invoice, UUID> {
    List<Invoice> findByClientId(UUID clientId, Sort sort);
    List<Invoice> findByProviderId(UUID providerId, Sort sort);
    List<Invoice> findByClientIdAndProviderId(UUID clientId, UUID providerId, Sort sort);

}
