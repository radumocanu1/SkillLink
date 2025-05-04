package unibuc.SkillLink.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unibuc.SkillLink.models.Provider;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProvidersRepository extends CrudRepository<Provider, UUID> {
    @Override
    List<Provider> findAll();

}
