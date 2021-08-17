package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.PredictionState;

/**
 * Represents a twitch channel point prediction.
 */
public interface Prediction extends IDable<String> {

    /**
     * Returns the streamer.
     * @return the streamer.
     */
    User getStreamer();

    /**
     * Returns the title of the prediction.
     * @return the title.
     */
    String getTitle();

    /**
     * Returns the title of the first outcome.
     * @return the first outcome's title.
     */
    String getOutcomeOne();

    /**
     * Returns the title of the second outcome.
     * @return the second outcome's title.
     */
    String getOutcomeTwo();

    /**
     * Returns the prediction window.
     * @return the prediction window.
     */
    int getPredictionWindow();

    /**
     * Returns the state of the prediction.
     * @return the prediction state.
     */
    PredictionState getState();

}
