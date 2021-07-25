package de.arnomann.martin.jta.api;

import de.arnomann.martin.jta.api.entities.*;
import de.arnomann.martin.jta.api.events.Listener;

import java.util.EnumSet;
import java.util.List;

/**
 * A bot.
 */
public interface JTABot {

    /**
     * Checks, whether the token is valid or not.
     *
     * @return whether the token is valid or not.
     */
    boolean isTokenValid();

    /**
     * "Generates" a new token. <b>The token should not be saved in a variable because tokens get invalidated after a minute.</b>
     *
     * @return the new token.
     */
    String getToken();

    /**
     * Returns the client id.
     *
     * @return the client id
     */
    String getClientId();

    /**
     * Sets the redirect uri used when getting the token.
     * @param redirectUri the redirect uri.
     */
    void setRedirectUri(String redirectUri);

    /**
     * Stops the bot.
     */
    void stop();

    /**
     * Gets a user by name.
     *
     * @param name the name to search for.
     * @return the user. <code>null</code>, if no user was found.
     */
    User getUserByName(String name);

    /**
     * Gets a user by id.
     *
     * @param id the id to search for.
     * @return the user. <code>null</code>, if no user was found.
     */
    User getUserById(long id);

    /**
     * Gets a team by name.
     *
     * @param name the name to search for.
     * @return the team. <code>null</code>, if no video was found.
     */
    Team getTeamByName(String name);

    /**
     * Gets a team by id.
     *
     * @param id the id to search for.
     * @return the team. <code>null</code>, if no team was found.
     */
    Team getTeamById(long id);

    /**
     * Gets a video by id.
     * @param id the id to search for.
     * @return the video. <code>null</code>, if no video was found.
     */
    Video getVideoById(long id);

    /**
     * Registers event listeners.
     *
     * @param first the first listener.
     * @param more  more listeners.
     * @return the bot.
     */
    JTABot registerEventListeners(Listener first, Listener... more);

    /**
     * Removes event listeners.
     *
     * @param first the first listener.
     * @param more  more listeners.
     * @return the bot.
     */
    JTABot removeEventListeners(Listener first, Listener... more);

    /**
     * Sets the OAuth token of the bot.
     *
     * @param token the new OAuth token.
     */
    void setChatOAuthToken(String token);

    /**
     * Returns the chat OAuth token.
     *
     * @return the token.
     */
    String getChatOAuthToken();

    /**
     * Gets a clip by slug.
     *
     * @param slug the slug.
     * @return the clip. <code>null</code>, if no clip was found.
     */
    Clip getClipBySlug(String slug);

    /**
     * Adds permissions to the needed permissions.
     * @param permissions the permission to add.
     */
    void addNeededPermissions(EnumSet<Permission> permissions);

    /**
     * Removes permissions to the needed permissions.
     * @param permissions the permission to remove.
     */
    void removeNeededPermissions(EnumSet<Permission> permissions);

    /**
     * Returns a list of global chat badges.
     * @return the global chat badges.
     */
    List<ChatBadge> getGlobalChatBadges();

}
