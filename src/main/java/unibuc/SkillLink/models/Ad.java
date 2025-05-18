package unibuc.SkillLink.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "ads")

public class Ad extends BaseModel<UUID> {

    @ManyToOne
    @Getter
    @Setter
    private Provider provider;

    @Getter
    @Setter
    int rate;

    @Getter
    @Setter
    String description;

    @Getter
    @Setter
    String picture;

    @Getter
    @Setter
    String title;
}
