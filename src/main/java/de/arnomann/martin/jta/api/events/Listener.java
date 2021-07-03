package de.arnomann.martin.jta.api.events;

public interface Listener {
    default void onMessageReceived(MessageReceivedEvent event) {}
    default void onSlashCommand(SlashCommandEvent event) {}
}
