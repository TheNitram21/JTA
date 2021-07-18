package de.arnomann.martin.jta.api.events;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Message;

/**
 * Represents a event that gets triggered when a slash command is received.
 */
public class SlashCommandEvent extends Event {

    protected final Message message;

    /**
     * Creates a slash command event. <b>This should not be used by the user.</b>
     * @param bot the bot.
     * @param message the message.
     */
    public SlashCommandEvent(JTABot bot, Message message) {
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

}
