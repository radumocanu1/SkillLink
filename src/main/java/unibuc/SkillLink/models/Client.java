package unibuc.SkillLink.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "clients")
@Data
public class Client extends BaseModel<UUID> {
    String firstName;
    String lastName;
    @ManyToMany
    @JoinTable(
            name = "clients_providers",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "provider_id")
    )
    Set<Provider> providers = new HashSet<>();
}
