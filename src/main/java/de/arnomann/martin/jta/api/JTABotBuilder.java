package de.arnomann.martin.jta.api;

import de.arnomann.martin.jta.api.util.Checks;
import de.arnomann.martin.jta.internal.JTABotImpl;

public class JTABotBuilder {

    /**
     * Creates a new bot with a client id and a client secret
     * @param clientId the client id (can be found in the twitch developer console)
     * @param clientSecret the client secret (can be found in the twitch developer console)
     * @return a new bot.
     */
    public static JTABotImpl create(String clientId, String clientSecret) {
        Checks.notEmpty(clientId, "Client ID");
        Checks.notEmpty(clientSecret, "Client Secret");

        return new JTABotImpl(clientId, clientSecret);
    }

}
