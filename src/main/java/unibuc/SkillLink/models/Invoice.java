package unibuc.SkillLink.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
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
    @NotBlank(message = "Description is required")
    @Size(min = 5, max = 200, message = "Description must be between 5 and 200 characters")

    String description;
    @Getter
    @Setter
    @NotNull(message = "Amount is required")
    @Digits(integer = 10, fraction = 2, message = "Amount must be a valid number with up to 2 decimal places")
    BigDecimal amount;
    @Getter
    @Setter
    @PastOrPresent(message = "Date cannot be in the future")

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
