package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.JTABot;
import org.jibble.pircbot.IrcException;

import java.io.IOException;

/**
 * Represents the chat of a {@link Channel}.
 */
public interface Chat {

    /**
     * Connects to the chat using the default OAuth token (set using {@link JTABot#setChatOAuthToken(String)}).
     * @param console Whether there should be console output or not. Should be deactivated in big chats.
     * @throws IrcException If the connection failed.
     * @throws IOException If the bot is already connected.
     */
    void connect(boolean console) throws IOException, IrcException;

    /**
     * Sends a message to the chat.
     * @param msg the message to send.
     */
    void sendMessage(String msg);

    /**
     * Disconnects from the chat.
     * @throws InterruptedException if the sleep was interrupted.
     */
    void disconnect() throws InterruptedException;

    /**
     * Returns the chat's channel.
     * @return the channel.
     */
    Channel getChannel();

    /**
     * Clears the chat.
     */
    void clear();

}
