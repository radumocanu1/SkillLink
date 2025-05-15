package unibuc.SkillLink.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "invoices")
@NoArgsConstructor
@AllArgsConstructor

public class Invoice extends BaseModel<UUID>{
    @Getter
    @Setter
    String description;
    @Getter
    @Setter
    Float amount;
    @Getter
    @Setter
    LocalDate dateCreated;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    @Getter
    @Setter
    Provider provider;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @Getter
    @Setter
    Client client;
}
