package de.arnomann.martin.jta.api;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a twitch user type.
 */
public enum UserType {
    STAFF("staff"),
    ADMIN("admin"),
    GLOBAL_MOD("global_mod"),
    DEFAULT("");

    private final String twitchType;

    UserType(String twitchType) {
        this.twitchType = twitchType;
    }

    public String getTwitchType() {
        return twitchType;
    }

    @NotNull
    public static UserType getByString(String type) {
        for (UserType userType : values()) {
            if (userType.getTwitchType().equalsIgnoreCase(type))
                return userType;
        }
        return DEFAULT;
    }

}
