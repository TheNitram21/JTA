package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.requests.UpdateAction;

public interface User extends Updatable {

    /**
     * Gets the id of the user as long.
     * @return the id.
     */
    long getId();

    /**
     * Checks, whether the user is live or not.
     * @return whether the user is live or not.
     */
    UpdateAction<Boolean> isLive();

    /**
     * Gets the name of the user.
     * @return the name of the user.
     */
    String getName();

    /**
     * Returns the chat of the user.
     * @return the chat.
     */
    Chat getChat();

    /**
     * Returns the current live stream of this user.
     * @return the stream.
     */
    UpdateAction<Stream> getStream();

}
