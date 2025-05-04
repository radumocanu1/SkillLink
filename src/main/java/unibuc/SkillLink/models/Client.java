package unibuc.SkillLink.models;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToMany
    Set<Provider> providers;
}
