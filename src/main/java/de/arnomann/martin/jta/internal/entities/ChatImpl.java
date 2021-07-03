package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.internal.JTAClass;
import de.arnomann.martin.jta.api.entities.Chat;
import de.arnomann.martin.jta.api.entities.User;
import de.arnomann.martin.jta.api.util.EntityUtils;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import java.io.IOException;

public class ChatImpl implements Chat, JTAClass {

    private final JTABot bot;
    private MessageSenderBot msgbot;
    private final User user;
    private boolean isConnected = false;

    public ChatImpl(User user, JTABot bot) {
        this.bot = bot;
        this.user = user;
    }

    @Override
    public void connect(String oauth, boolean console) throws NickAlreadyInUseException, IOException, IrcException {
        msgbot = new MessageSenderBot("jtabot", this.bot, user);
        msgbot.setVerbose(console);
        msgbot.connect("irc.twitch.tv", 6667, oauth);
        msgbot.joinChannel("#" + EntityUtils.userNameToId(user));
        isConnected = true;
    }

    @Override
    public ChatImpl sendMessage(String msg) {
        if(!isConnected) throw new JTAException("Not connected to chat!");
        msgbot.sendMessage("#" + EntityUtils.userNameToId(user), msg);
        return this;
    }

    @Override
    public void disconnect() throws InterruptedException {
        System.out.println("Disconnecting in 10 seconds...");
        Thread.sleep(10000);
        msgbot.disconnect();
        isConnected = false;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void clear() {
        sendMessage("/clear");
    }

}
