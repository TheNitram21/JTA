package de.arnomann.martin.jta.api.entities;

/**
 * Represents a chat message.
 */
public interface Message {

    /**
     * Returns the content of the message.
     * @return the content.
     */
    String getContent();

    /**
     * Returns the user who sent this message.
     * @return the sender.
     */
    User getSender();

    /**
     * Returns the chat the message was sent in.
     * @return the chat.
     */
    Chat getChannel();

}
