package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.requests.UpdateAction;

public interface Channel extends Updatable, IDable {

    /**
     * Returns the chat of the channel.
     * @return the chat.
     */
    Chat getChat();

    /**
     * Returns the current live stream of this channel.
     * @return the stream.
     */
    UpdateAction<Stream> getStream();

    /**
     * Checks, whether the channel is live or not.
     * @return whether the channel is live or not.
     */
    UpdateAction<Boolean> isLive();

    /**
     * Returns the user of the channel.
     * @return the user.
     */
    User getUser();

    /**
     * Returns the count of followers of the channel.
     * @return the follower count.
     */
    long getFollowerCount();

    /**
     * Returns the most recent hype train.
     * @return the hype train.
     */
    HypeTrain getHypeTrain();

}
