package de.arnomann.martin.jta.api.events;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Message;

public class SlashCommandEvent extends Event {

    protected final Message message;

    public SlashCommandEvent(JTABot bot, Message message) {
        super(bot);
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

}
