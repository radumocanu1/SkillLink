package unibuc.SkillLink.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
public class AvailabilitySlot extends BaseModel<UUID> {

    @Column(nullable = false)
    @Getter
    @Setter
    private LocalDate date;

    @Column(nullable = false)
    @Getter
    @Setter
    private LocalTime startTime;

    @Column(nullable = false)
    @Getter
    @Setter
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "calendar_id", nullable = false)
    @JsonIgnore
    @Getter
    @Setter
    private Calendar calendar;
}

