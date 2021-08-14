package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.requests.UpdateAction;

import java.awt.Color;

/**
 * Represents a twitch channel point reward.
 */
public interface ChannelPointReward extends Updatable, IDable<String> {

    /**
     * Returns the channel of this channel point reward.
     * @return the channel.
     */
    Channel getChannel();

    /**
     * Returns the background color of the reward.
     * @return the background color.
     */
    UpdateAction<Color> getBackgroundColor();

    /**
     * Returns if the channel point reward is enabled.
     * @return if the reward is enabled.
     */
    UpdateAction<Boolean> isEnabled();

    /**
     * Returns how many channel points have to be spent for the reward.
     * @return the cost.
     */
    UpdateAction<Integer> getCost();

    /**
     * Returns the title of the reward.
     * @return the title.
     */
    UpdateAction<String> getTitle();

    /**
     * Sets the background color of the reward.
     * @param color the new color.
     * @scopes channel:manage:redemptions
     */
    void setBackgroundColor(Color color);

    /**
     * Sets if the reward is enabled.
     * @param enabled if the reward is enabled.
     * @scopes channel:manage:redemptions
     */
    void setEnabled(boolean enabled);

    /**
     * Sets the cost of the reward.
     * @param cost the cost.
     * @scopes channel:manage:redemptions
     */
    void setCost(int cost);

    /**
     * Sets the title of the reward.
     * @param title the title.
     * @scopes channel:manage:redemptions
     */
    void setTitle(String title);

    /**
     * Deletes the channel point reward.
     * @scopes channel:manage:redemptions
     */
    void delete();

}
