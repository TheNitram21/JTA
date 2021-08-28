package de.arnomann.martin.jta.api;

import de.arnomann.martin.jta.api.util.Checks;
import de.arnomann.martin.jta.internal.JTABotImpl;

import java.util.Arrays;
import java.util.EnumSet;

/**
 * Builds bots.
 */
public class JTABotBuilder {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String chatOAuthToken;
    private EnumSet<Permission> neededPermissions;

    private JTABotBuilder(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;

        this.neededPermissions = EnumSet.noneOf(Permission.class);
    }

    /**
     * Creates a new bot with a client id and a client secret
     * @param clientId the client id (can be found in the twitch developer console)
     * @param clientSecret the client secret (can be found in the twitch developer console)
     * @return a new bot.
     */
    public static JTABotBuilder create(String clientId, String clientSecret) {
        Checks.notEmpty(clientId, "Client ID");
        Checks.notEmpty(clientSecret, "Client Secret");

        return new JTABotBuilder(clientId, clientSecret);
    }

    /**
     * Sets the new client id.
     * @param clientId the client id.
     * @return the builder after the operation was performed.
     */
    public JTABotBuilder setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * Sets the new client secret.
     * @param clientSecret the client secret.
     * @return the builder after the operation was performed.
     */
    public JTABotBuilder setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    /**
     * Sets the new redirect uri.
     * @param redirectUri the redirect uri.
     * @return the builder after the operation was performed.
     */
    public JTABotBuilder setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    /**
     * Sets the new chat oauth token.
     * @param chatOAuthToken the chat oauth token.
     * @return the builder after the operation was performed.
     */
    public JTABotBuilder setChatOAuthToken(String chatOAuthToken) {
        if(chatOAuthToken.startsWith("oauth:"))
            this.chatOAuthToken = chatOAuthToken;
        else
            this.chatOAuthToken = "oauth:" + chatOAuthToken;

        return this;
    }

    /**
     * Adds new needed permissions.
     * @param neededPermissions the new needed permissions.
     * @return the builder after the operation was performed.
     */
    public JTABotBuilder addNeededPermissions(Permission... neededPermissions) {
        this.neededPermissions.addAll(Arrays.asList(neededPermissions));
        return this;
    }

    /**
     * Removes needed permissions.
     * @param neededPermissions the needed permissions to remove.
     * @return the builder after the operation was performed.
     */
    public JTABotBuilder removeNeededPermissions(Permission... neededPermissions) {
        Arrays.asList(neededPermissions).forEach(this.neededPermissions::remove); // IntelliJ said that this would be faster than removeAll()
        return this;
    }

    /**
     * Builds the bot.
     * @return the built bot.
     */
    public JTABotImpl build() {
        JTABotImpl bot = new JTABotImpl(clientId, clientSecret);

        if(!redirectUri.isEmpty())
            bot.setRedirectUri(redirectUri);

        bot.setChatOAuthToken(chatOAuthToken);
        bot.addNeededPermissions(neededPermissions);

        return bot;
    }

}
