package de.arnomann.martin.jta.api.util;

import de.arnomann.martin.jta.api.entities.User;

public class EntityUtils {

    /**
     * Converts the name of the user to a valid twitch id.
     * @param user the user
     * @return the id
     */
    public static String userNameToId(User user) {
        return userNameToId(user.getName());
    }

    /**
     * Converts the name of a user to a valid twitch id.
     * @param name the name of the user
     * @return the id
     */
    public static String userNameToId(String name) {
        return name.replaceAll(" ", "_").toLowerCase();
    }

}
