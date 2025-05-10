package unibuc.SkillLink.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unibuc.SkillLink.models.Client;
import java.util.List;
import java.util.UUID;

@Repository
public interface ClientsRepository extends CrudRepository<Client, UUID> {
    @Override
    List<Client> findAll();

}
