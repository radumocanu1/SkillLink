package unibuc.SkillLink.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@MappedSuperclass
@NoArgsConstructor
@Data
public class BaseModel<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    T id;

    @UpdateTimestamp
    private Instant lastUpdated;
}
