package de.arnomann.martin.jta.api.entities;

/**
 * Represents a twitch chat badge.
 */
public interface ChatBadge extends IDable<String> {

    /**
     * Returns the image url of the chat badge.
     * @return the image url.
     */
    String getImageUrl();

}
