package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.BroadcasterType;
import de.arnomann.martin.jta.api.requests.UpdateAction;

public interface User extends Updatable, IDable {

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

}
