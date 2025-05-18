package unibuc.SkillLink.commands.bookings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class CheckBookingAvailabilityCommand implements ICommand<Boolean> {
    String providerUsername;
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;
} 