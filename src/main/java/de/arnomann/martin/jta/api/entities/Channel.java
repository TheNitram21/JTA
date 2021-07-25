package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.api.exceptions.JTAException;

import java.util.List;

/**
 * Represents a twitch channel that can go live.
 */
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

    /**
     * Returns the team in which the channel is.
     * @return the team.
     * @throws JTAException if the channel is in no team.
     */
    Team getTeam();

    /**
     * Returns a list containing all chat badges in this channel's chat.
     * @return the chat badges.
     */
    List<ChatBadge> getChatBadges();

}
