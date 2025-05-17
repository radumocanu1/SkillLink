package unibuc.SkillLink.helpers;

import unibuc.SkillLink.abstractions.ICommand;
import unibuc.SkillLink.commands.clients.CreateClientCommand;
import unibuc.SkillLink.commands.providers.CreateProviderCommand;
import unibuc.SkillLink.models.Calendar;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Provider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EntityUserTypesMappings {

    public static ICommand<?> getCreateEntityCommand(UserType userType, String username, String firstName, String lastName) {
        switch (userType) {
            case CLIENT -> {
                return new CreateClientCommand(new Client(firstName,lastName,username, new HashSet<>()));
            }
            case PROVIDER -> {
                return new CreateProviderCommand(new Provider(firstName,lastName,username, new HashSet<>(), new HashSet<>(), new Calendar()));
            }
        }
        throw new IllegalArgumentException("Unknown user type " + userType);
    }
}
