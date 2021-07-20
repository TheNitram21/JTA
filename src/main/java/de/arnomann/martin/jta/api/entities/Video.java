package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.requests.UpdateAction;

/**
 * Represents a twitch video.
 */
public interface Video extends IDable, Updatable {

    /**
     * Returns who streamed the video.
     * @return the streamer.
     */
    User getStreamer();

    /**
     * Returns the title of the video.
     * @return the title.
     */
    UpdateAction<String> getTitle();

    /**
     * Returns the description of the video.
     * @return the description.
     */
    UpdateAction<String> getDescription();

    /**
     * Returns how often the video was watched.
     * @return the views.
     */
    UpdateAction<Long> getViews();

}
