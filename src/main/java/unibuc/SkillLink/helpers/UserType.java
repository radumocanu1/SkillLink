package unibuc.SkillLink.helpers;

import lombok.ToString;

@ToString
public enum UserType {
    CLIENT,
    PROVIDER;

    public static UserType fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("UserType cannot be null");
        }
        return switch (value.toUpperCase()) {
            case "CLIENT" -> CLIENT;
            case "PROVIDER" -> PROVIDER;
            default -> throw new IllegalArgumentException("Unknown user type: " + value);
        };
    }
}
