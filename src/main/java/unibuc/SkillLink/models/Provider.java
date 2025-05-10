package unibuc.SkillLink.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "providers")
public class Provider extends BaseModel<UUID> {
    String firstName;
    String lastName;
    @ManyToMany(mappedBy = "providers")
    Set<Client> clients = new HashSet<>();

    public void addClient(Client client) {
        clients.add(client);
    }
}
