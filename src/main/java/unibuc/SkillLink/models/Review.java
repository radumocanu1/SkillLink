package unibuc.SkillLink.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review extends BaseModel<UUID>{
    @Getter
    @Setter
    String content;

    @Getter
    @Setter
    Integer stars;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    @JsonIgnoreProperties("reviews")
    @Setter
    @Getter
    Provider provider;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties("providers")
    @Setter
    @Getter
    Client client;

}
