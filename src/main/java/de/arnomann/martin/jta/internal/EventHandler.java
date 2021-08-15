package de.arnomann.martin.jta.internal;

import de.arnomann.martin.jta.api.events.Event;
import de.arnomann.martin.jta.api.events.Listener;
import de.arnomann.martin.jta.api.events.MessageReceivedEvent;
import de.arnomann.martin.jta.api.events.SlashCommandEvent;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {

    private EventHandler() {}

    private static final List<Listener> registeredEventListeners = new ArrayList<>();

    /**
     * Handles an event.
     * @param event the event to handle.
     */
    public static void onEvent(Event event) {
        for(Listener l : registeredEventListeners) {
            if (event instanceof MessageReceivedEvent) l.onMessageReceived((MessageReceivedEvent) event);
            if (event instanceof SlashCommandEvent) l.onSlashCommand((SlashCommandEvent) event);
        }
    }

    /**
     * Adds an event listener to the registered event listeners.
     * @param listener the listener to add.
     */
    public static void registerEventListener(Listener listener) {
        registeredEventListeners.add(listener);
    }

    /**
     * Removes an event listener from the registered event listeners.
     * @param listener the listener to remove.
     */
    public static void removeEventListener(Listener listener) {
        registeredEventListeners.remove(listener);
    }
}
