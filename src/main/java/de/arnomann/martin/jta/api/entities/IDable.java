package de.arnomann.martin.jta.api.entities;

/**
 * Marks classes of which instances can have an id.
 * @param <T> The type of id (String or Long)
 */
public interface IDable<T> {
    /**
     * Returns the id of this object.
     * @return the id.
     */
    T getId();
}
