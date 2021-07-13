package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.requests.UpdateAction;

public interface User extends Updatable, IDable {

    /**
     * Gets the name of the user.
     * @return the name of the user.
     */
    String getName();

    /**
     * Returns the bio of the user.
     * @return the bio.
     */
    UpdateAction<String> getBio();

    /**
     * Returns the channel of the user.
     * @return the channel.
     */
    Channel getChannel();

}
