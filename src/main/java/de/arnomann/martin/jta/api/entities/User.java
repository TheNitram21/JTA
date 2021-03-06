package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.BroadcasterType;
import de.arnomann.martin.jta.api.UserType;
import de.arnomann.martin.jta.api.requests.UpdateAction;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a twitch user.
 */
public interface User extends Updatable, IDable<Long> {

    /**
     * Gets the name of the user.
     * @return the name of the user.
     */
    String getName();

    /**
     * Returns the channel of the user.
     * @return the channel.
     */
    Channel getChannel();

    /**
     * Returns the broadcaster type of the user.
     * @return the broadcaster type.
     */
    UpdateAction<BroadcasterType> getBroadcasterType();

    /**
     * Returns the type of the user.
     * @return the user type.
     */
    UpdateAction<UserType> getUserType();

    /**
     * Returns the stream schedule for this user.
     * @return the schedule.
     */
    StreamSchedule getStreamSchedule();

    /**
     * Returns a list containing all followed channels.
     * @return the follows.
     */
    List<Channel> getFollows();

    /**
     * Checks, if the user is following the specified channel.
     * @param channel the channel to check.
     * @return if the user follows the channel.
     * @see User#getFollows()
     */
    boolean isFollowing(Channel channel);

    /**
     * Returns a list containing all blocked users.
     * @return the blocked users.
     * @scopes user:read:blocked_users
     */
    List<User> getBlockedUsers();

    /**
     * Returns when the user was created.
     * @return the creation time.
     */
    LocalDateTime getCreationTime();

}
