package de.arnomann.martin.jta.api.events;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.internal.util.Helpers;
import de.arnomann.martin.jta.api.entities.Message;

public class MessageReceivedEvent extends Event {

    protected final Message message;

    public MessageReceivedEvent(JTABot bot, Message message) {
        super(bot);
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return Helpers.format("[{} | #{}]: {}", getMessage().getSender().getName(), getMessage().getChannel().getUser().getName(),
                getMessage().getContent());
    }
}
