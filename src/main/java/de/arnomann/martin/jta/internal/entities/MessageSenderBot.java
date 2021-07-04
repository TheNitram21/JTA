package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Chat;
import de.arnomann.martin.jta.api.entities.User;
import de.arnomann.martin.jta.api.events.MessageReceivedEvent;
import de.arnomann.martin.jta.api.events.SlashCommandEvent;
import de.arnomann.martin.jta.internal.EventHandler;
import de.arnomann.martin.jta.internal.JTAClass;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.IOException;

public class MessageSenderBot extends PircBot implements JTAClass {

    private JTABot bot;
    private User user;

    MessageSenderBot(String name, JTABot bot, User user) {
        this.setName(name);
        this.bot = bot;
        this.user = user;
    }

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        try {
            Chat c = bot.getUserByName(channel.substring(1)).getChat();
            c.connect(bot.getChatOAuthToken(), false);
            if (message.startsWith("/"))
                EventHandler.onEvent(new SlashCommandEvent(bot, new MessageImpl(bot, message.substring(1), bot.getUserByName(sender),
                        c)));
            else
                EventHandler.onEvent(new MessageReceivedEvent(bot, new MessageImpl(bot, message, bot.getUserByName(sender), c)));
        } catch (IOException | IrcException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(User channel, String msg) {
        sendMessage("#" + channel.getName(), msg);
    }

}
