package de.arnomann.martin.jta.api.entities;

/**
 * Marks classes which instances can have an id.
 */
public interface IDable {
    /**
     * Returns the id of this object.
     * @return the id.
     */
    long getId();
}
