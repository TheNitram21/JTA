package de.arnomann.martin.jta.api;

/**
 * Represents a poll state.
 */
public enum PollState {
    ACTIVE,
    COMPLETED,
    TERMINATED,
    ARCHIVED,
    MODERATED,
    INVALID;
}
