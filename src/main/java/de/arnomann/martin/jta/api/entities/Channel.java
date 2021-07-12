package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.requests.UpdateAction;

public interface Channel extends Updatable {

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

    /**
     * Checks, whether the user is live or not.
     * @return whether the user is live or not.
     */
    UpdateAction<Boolean> isLive();

    /**
     * Returns the user of the channel.
     * @return the user.
     */
    User getUser();
}
