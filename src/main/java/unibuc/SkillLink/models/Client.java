package unibuc.SkillLink.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "clients")
public class Client extends BaseModel<UUID> {
    @ManyToMany
    Set<Provider> providers = new HashSet<>();
}
