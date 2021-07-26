package de.arnomann.martin.jta.api.entities;

/**
 * Represents a twitch chat emote.
 */
public interface Emote extends IDable {

    /**
     * Returns the name of the emote (e.g.: {@code Kappa})
     * @return the name.
     */
    String getName();

    /**
     * Returns the image url of the emote.
     * @return the url.
     */
    String getImageUrl();

    /**
     * Returns if the emote can be used everywhere by everyone or not.
     * @return if the emote is global.
     */
    boolean isGlobal();

    /**
     * Returns the channel of the emote. {@code null}, if {@link Emote#isGlobal()} returns {@code false}.
     * @return the channel.
     */
    Channel getChannel();

}
