package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Channel;
import de.arnomann.martin.jta.api.entities.Chat;
import de.arnomann.martin.jta.api.entities.User;
import de.arnomann.martin.jta.api.events.MessageReceivedEvent;
import de.arnomann.martin.jta.api.events.SlashCommandEvent;
import de.arnomann.martin.jta.internal.EventHandler;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.IOException;

public class MessageSenderBot extends PircBot {

    private final JTABot bot;
    private final String channelName;

    MessageSenderBot(String name, JTABot bot, String channelName) {
        this.setName(name);
        this.bot = bot;
        this.channelName = channelName;
    }

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        try {
            Chat c = bot.getUserByName(channelName).getChannel().getChat();
            c.connect(false);
            if (message.startsWith("/"))
                EventHandler.onEvent(new SlashCommandEvent(bot, new MessageImpl(bot, message.substring(1), bot.getUserByName(sender),
                        c)));
            else
                EventHandler.onEvent(new MessageReceivedEvent(bot, new MessageImpl(bot, message, bot.getUserByName(sender), c)));
        } catch (IOException | IrcException e) {
            e.printStackTrace();
        }

    }

}
