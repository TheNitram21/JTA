package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Chat;
import de.arnomann.martin.jta.api.entities.Message;
import de.arnomann.martin.jta.api.entities.User;

public class MessageImpl implements Message {

    private final JTABot bot;
    private final String content;
    private final User sender;
    private final Chat channel;

    public MessageImpl(JTABot bot, String content, User sender, Chat channel) {
        this.bot = bot;
        this.content = content;
        this.sender = sender;
        this.channel = channel;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public User getSender() {
        return sender;
    }

    @Override
    public Chat getChannel() {
        return channel;
    }
}
