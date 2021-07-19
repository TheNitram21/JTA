package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.requests.UpdateAction;

import java.util.List;

/**
 * Represents a twitch team.
 */
public interface Team extends Updatable, IDable {

    /**
     * Returns the members of the team.
     * @return the members.
     */
    UpdateAction<List<User>> getMembers();

    /**
     * Returns the display name of the team.
     * @return the name.
     */
    UpdateAction<String> getName();

    /**
     * Returns the id of the team.
     * @return the id.
     */
    UpdateAction<String> getInfo();

}
