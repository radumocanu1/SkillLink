package unibuc.SkillLink.commands.calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.models.Calendar;

@AllArgsConstructor
@Getter
public class CreateCalendarCommand implements ICommand<Calendar> {
    Calendar calendar;
}
