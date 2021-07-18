package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.requests.UpdateAction;

/**
 * Represents a hype train which occurs when a stream has got a set amount of subscriptions / cheers.
 */
public interface HypeTrain extends Updatable {

    /**
     * Indicates how much the hype train has progressed.
     */
    enum HypeTrainState {
        NOT_ACTIVE(0), LEVEL_1(1), LEVEL_2(2), LEVEL_3(3), LEVEL_4(4), LEVEL_5(5);

        private final int level;

        HypeTrainState(int level) {
            this.level = level;
        }

        public int getLevel() { return level; }

        public static HypeTrainState getByInt(int level) {
            for(HypeTrainState state : values()) {
                if(state.getLevel() == level) {
                    return state;
                }
            }
            return NOT_ACTIVE;
        }
    }

    /**
     * Returns the current level of the hype train.
     * @return the hype train level.
     */
    UpdateAction<HypeTrainState> getLevel();

    /**
     * Returns the points of the hype train.
     * @return the points.
     */
    UpdateAction<Integer> getPoints();

    /**
     * Returns the channel in which the hype train started.
     * @return the channel.
     */
    Channel getChannel();

    /**
     * Returns the last contributor to the hype train.
     * @return the last contributor.
     */
    UpdateAction<User> getLastContributor();

}
