package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.PredictionState;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.api.exceptions.JTAException;

import java.util.List;
import java.util.Locale;

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

    /**
     * Returns a list containing all custom emotes of this channel.
     * @return the emotes.
     */
    List<Emote> getCustomEmotes();

    /**
     * Returns a list containing all channel moderators.
     * @return the moderators.
     * @scopes moderation:read
     */
    List<User> getModerators();

    /**
     * Returns a list containing all banned users.
     * @return the banned users.
     * @scopes moderation:read
     */
    List<User> getBannedUsers();

    /**
     * Sets the title of the stream.
     * @param title the new title.
     * @scopes channel:manage:broadcast
     */
    void setStreamTitle(String title);

    /**
     * Sets the language of the channel's stream.
     * @param locale the locale of the language.
     * @scopes channel:manage:broadcast
     */
    void setStreamLanguage(Locale locale);

    /**
     * Starts a prediction in this channel.
     * @param title the title.
     * @param answerOne the first answer.
     * @param answerTwo the second answer.
     * @param time the time of the prediction in seconds. Maximal 1800.
     * @scopes channel:manage:predictions
     */
    void startPrediction(String title, String answerOne, String answerTwo, int time);

    /**
     * Starts a poll in this channel.
     * @param title the title.
     * @param choices the choices. Array must not be bigger than 5.
     * @param time the duration of the poll in seconds. Maximal 1800.
     * @scopes channel:manage:polls
     */
    void startPoll(String title, String[] choices, int time);

}
