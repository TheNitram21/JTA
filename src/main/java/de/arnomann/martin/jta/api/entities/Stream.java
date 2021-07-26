package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.requests.UpdateAction;

import java.time.LocalDateTime;

/**
 * Represents a live stream.
 */
public interface Stream extends Updatable, IDable {

    /**
     * Returns the streaming channel.
     * @return the streamer.
     */
    Channel getChannel();

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

    /**
     * Returns when the stream started.
     * @return the starting time.
     */
    LocalDateTime getWhenStarted();

}
