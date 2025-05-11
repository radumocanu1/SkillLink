package unibuc.SkillLink.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ToString(exclude = {"clients"})
@EqualsAndHashCode(callSuper = true, exclude = {"clients"})@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "providers")
public class Provider extends BaseModel<UUID> implements AppUser {
    @Getter
    @Setter
    String firstName;
    @Getter
    @Setter
    String lastName;
    @Getter
    String username;

    @JsonBackReference
    @ManyToMany(mappedBy = "providers")
    Set<Client> clients = new HashSet<>();

    public void addClient(Client client) {
        clients.add(client);
    }
}
