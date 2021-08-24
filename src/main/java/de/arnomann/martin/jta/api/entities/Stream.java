package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.AdLength;
import de.arnomann.martin.jta.api.requests.UpdateAction;

import java.time.LocalDateTime;

/**
 * Represents a live stream.
 */
public interface Stream extends Updatable, IDable<Long > {

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
     * Returns when the stream started.
     * @return the starting time.
     */
    LocalDateTime getWhenStarted();

    /**
     * Starts an ad in this stream.
     * @param length the length of the ad.
     * @scopes channel:edit:commercial
     */
    void startAd(AdLength length);

}
