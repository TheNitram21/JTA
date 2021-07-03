package de.arnomann.martin.jta.api;

import de.arnomann.martin.jta.api.entities.User;
import de.arnomann.martin.jta.api.events.Listener;

/**
 * A bot.
 */
public interface JTABot {

    /**
     * Checks, whether the token is valid or not.
     * @return whether the token is valid or not.
     */
    public boolean isTokenValid();

    /**
     * "Generates" a new token. <b>The token should not be saved in a variable because tokens get invalidated after a minute.</b>
     * @return the new token.
     */
    public String getToken();

    /**
     * Returns the client id.
     * @return the client id
     */
    public String getClientId();

    /**
     * Stops the bot.
     */
    public void stop();

    /**
     * Gets a user by name.
     * @param name the name to search for.
     * @return the user. <code>null</code>, if no user was found.
     */
    public User getUserByName(String name);

    /**
     * Gets a user by id.
     * @param id the id to search for.
     * @return the user. <code>null</code>, if no user was found.
     */
    public User getUserById(long id);

    /**
     * Registers event listeners.
     * @param first the first listener.
     * @param more more listeners.
     * @return the bot.
     */
    public JTABot registerEventListeners(Listener first, Listener... more);

    /**
     * Removes event listeners.
     * @param first the first listener.
     * @param more more listeners.
     * @return the bot.
     */
    public JTABot removeEventListeners(Listener first, Listener... more);

}
