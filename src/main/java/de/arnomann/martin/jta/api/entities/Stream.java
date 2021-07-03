package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.requests.UpdateAction;

/**
 * Represents a live stream.
 */
public interface Stream extends Updatable {

    /**
     * Returns the streaming user.
     * @return the streamer.
     */
    User getUser();

    /**
     * Returns the viewer count.
     * @return the viewers.
     */
    UpdateAction<Long> getViewers();

    /**
     * Returns the game of the stream.
     * @return the game.
     */
    UpdateAction<Game> getGame();

}
