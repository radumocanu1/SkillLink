package unibuc.SkillLink.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
public class Client extends BaseModel<UUID> implements AppUser {
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
    @Setter
    String profilePicture;

    @ManyToMany
    @JsonIgnore
    @Getter
    @JoinTable(
            name = "clients_providers",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "provider_id")
    )
    Set<Provider> providers = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private Set<Booking> bookings;

    public void addProvider(Provider p) {
        providers.add(p);
    }

    @Override
    public Set<Booking> getBookings() {
        return bookings;
    }
}
