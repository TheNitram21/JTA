package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.PollState;

import java.util.List;

/**
 * Represents a twitch poll.
 */
public interface Poll extends IDable<String> {

    /**
     * Returns the streamer.
     * @return the streamer.
     */
    User getStreamer();

    /**
     * Returns the title of the poll.
     * @return the title.
     */
    String getTitle();

    /**
     * Returns the possible choices.
     * @return the choices.
     */
    List<String> getChoices();

    /**
     * Returns the state of the prediction.
     * @return the prediction state.
     */
    PollState getState();

    /**
     * Returns the prediction's duration.
     * @return the duration.
     */
    int getDuration();

}
