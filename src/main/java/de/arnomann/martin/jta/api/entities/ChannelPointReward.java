package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.requests.UpdateAction;

import java.awt.Color;

public interface ChannelPointReward extends Updatable {

    /**
     * Returns the channel of this channel point reward.
     * @return the channel.
     */
    Channel getChannel();

    /**
     * Returns the id of the reward.
     * @return the id.
     */
    String getId();

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

}
