package de.arnomann.martin.jta.api.entities;

/**
 * Marks classes that can be updated.
 */
public interface Updatable {
    /**
     * Updates the JSON-information for this object.
     */
    void update();
}
