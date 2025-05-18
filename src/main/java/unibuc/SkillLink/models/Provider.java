package unibuc.SkillLink.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Setter
    String username;
    @Getter
    @Setter
    double rate;
    @Getter
    @Setter
    String profilePicture;

    @Getter
    @JsonIgnore
    @ManyToMany(mappedBy = "providers",cascade = CascadeType.ALL)
    Set<Client> clients = new HashSet<>();

    @OneToMany(mappedBy = "provider")
    @Getter
    @Setter
    @JsonIgnore
    Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "provider")
    Set<Booking> bookings;

    @OneToMany(mappedBy = "provider")
    Set<Ad> ads;

    @Override
    public Set<Booking> getBookings() {
        return bookings;
    }

    public void addAd(Ad ad) {
        ads.add(ad);
    }
}
