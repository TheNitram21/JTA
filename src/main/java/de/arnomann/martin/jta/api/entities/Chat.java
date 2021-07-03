package de.arnomann.martin.jta.api.entities;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import java.io.IOException;

public interface Chat {

    /**
     * Connects to the chat.
     * @param oauth The OAuth token, you can get one at <a href="https://twitchapps.com/tmi/">https://twitchapps.com/tmi/</a>
     * @param console Whether there should be console output or not. Should be deactivated in big chats.
     * @throws IrcException If the connection failed.
     * @throws IOException If the bot is already connected.
     * @throws NickAlreadyInUseException If the nick name is already in use.
     */
    void connect(String oauth, boolean console) throws NickAlreadyInUseException, IOException, IrcException;

    /**
     * Sends a message to the chat.
     * @param msg the message to send.
     */
    Chat sendMessage(String msg);

    /**
     * Disconnects from the chat.
     * @throws InterruptedException if the sleep was interrupted.
     */
    void disconnect() throws InterruptedException;

    /**
     * Returns the chat's user.
     * @return the user.
     */
    User getUser();

    /**
     * Clears the chat.
     */
    void clear();

}
