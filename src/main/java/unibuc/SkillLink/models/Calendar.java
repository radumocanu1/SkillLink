package unibuc.SkillLink.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Table(name="calendars")
@NoArgsConstructor
public class Calendar extends BaseModel<UUID> {
    @OneToOne
    @JoinColumn(name = "provider_id")
    @Getter
    @Setter
    private Provider provider;

    @Getter
    @Setter
    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<AvailabilitySlot> slots = new ArrayList<>();

}