package de.arnomann.martin.jta.api.entities;

/**
 * Represents a stream game.
 */
public interface Game extends IDable<Long> {

    /**
     * Returns the name of the game.
     * @return the name.
     */
    String getName();

}
