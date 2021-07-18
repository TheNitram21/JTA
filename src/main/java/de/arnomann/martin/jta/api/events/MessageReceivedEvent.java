package de.arnomann.martin.jta.api.events;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.internal.util.Helpers;
import de.arnomann.martin.jta.api.entities.Message;

/**
 * Represents a event that gets triggered when a message is received.
 */
public class MessageReceivedEvent extends Event {

    protected final Message message;

    /**
     * Creates a message received event. <b>This should not be used by the user.</b>
     * @param bot the bot.
     * @param message the message.
     */
    public MessageReceivedEvent(JTABot bot, Message message) {
        super(bot);
        this.message = message;
    }

    /**
     * Returns the message which was received.
     * @return the message.
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Converts the event into a String representation.
     * @return the event as a String.
     */
    @Override
    public String toString() {
        return Helpers.format("[{} | #{}]: {}", getMessage().getSender().getName(), getMessage().getChannel().getChannel().getUser().getName(),
                getMessage().getContent());
    }
}
