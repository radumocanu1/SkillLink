package unibuc.SkillLink.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "provider")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String firstName;
    String lastName;
    @OneToMany
    Set<Client> client;
}
