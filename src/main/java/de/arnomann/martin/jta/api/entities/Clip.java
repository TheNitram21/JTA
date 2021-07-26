package de.arnomann.martin.jta.api.entities;

import java.time.LocalDateTime;

/**
 * Represents a clip of a live stream.
 */
public interface Clip {

    /**
     * Returns the slug of the clip.
     *
     * @return the slug.
     */
    String getSlug();

    /**
     * Returns the title of the clip.
     *
     * @return the title.
     */
    String getTitle();

    /**
     * Returns the creator of the clip.
     *
     * @return the creator.
     */
    User getCreator();

    /**
     * Returns the channel in which the clip was created.
     * @return the channel.
     */
    User getChannel();

    /**
     * Returns the URL of the clip.
     *
     * @return the URL.
     */
    String getUrl();

    /**
     * Returns when the clip was created.
     * @return the creation time.
     */
    LocalDateTime getCreationTime();

}
