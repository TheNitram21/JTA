package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Channel;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.entities.Chat;
import de.arnomann.martin.jta.api.entities.User;
import de.arnomann.martin.jta.api.util.EntityUtils;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import java.io.IOException;

public class ChatImpl implements Chat {

    private final JTABot bot;
    private MessageSenderBot msgbot;
    private final Channel channel;
    private boolean isConnected = false;

    public ChatImpl(Channel channel, JTABot bot) {
        this.bot = bot;
        this.channel = channel;
    }

    @Override
    public void connect(String oauth, boolean console) throws IOException, IrcException {
        msgbot = new MessageSenderBot("jtabot", this.bot, "#" + channel.getUser().getName());
        msgbot.setVerbose(console);
        msgbot.connect("irc.twitch.tv", 6667, oauth);
        msgbot.joinChannel("#" + EntityUtils.userNameToId(channel.getUser()));
        isConnected = true;
    }

    @Override
    public void connect(boolean console) throws IOException, IrcException {
        msgbot = new MessageSenderBot("jtabot", this.bot, "#" + channel.getUser().getName());
        msgbot.setVerbose(console);
        msgbot.connect("irc.twitch.tv", 6667, this.bot.getChatOAuthToken());
        msgbot.joinChannel("#" + EntityUtils.userNameToId(channel.getUser()));
        isConnected = true;
    }

    @Override
    public void sendMessage(String msg) {
        if(!isConnected) throw new JTAException("Not connected to chat!");
        msgbot.sendMessage("#" + EntityUtils.userNameToId(channel.getUser()), msg);
    }

    @Override
    public void disconnect() throws InterruptedException {
        System.out.println("Disconnecting in 10 seconds...");
        Thread.sleep(10000);
        msgbot.disconnect();
        isConnected = false;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public void clear() {
        sendMessage("/clear");
    }

}
