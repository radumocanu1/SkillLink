package unibuc.SkillLink.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
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
    @Setter
    String username;

    @Getter
    @JsonIgnore
    @ManyToMany(mappedBy = "providers")
    Set<Client> clients = new HashSet<>();

    public void addClient(Client client) {
        clients.add(client);
    }

    @OneToMany(mappedBy = "provider")
    @Getter
    @Setter
    @JsonIgnore
    Set<Review> reviews = new HashSet<>();
}
