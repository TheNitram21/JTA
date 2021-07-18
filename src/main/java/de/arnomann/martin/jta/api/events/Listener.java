package de.arnomann.martin.jta.api.events;

/**
 * An event listener.
 */
public interface Listener {
    // Chat events
    default void onMessageReceived(MessageReceivedEvent event) {}
    default void onSlashCommand(SlashCommandEvent event) {}
}
