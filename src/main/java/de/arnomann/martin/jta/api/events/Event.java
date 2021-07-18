package de.arnomann.martin.jta.api.events;

import de.arnomann.martin.jta.api.JTABot;

/**
 * An event.
 */
public class Event {

    protected final JTABot bot;

    /**
     * Creates a new event. <b>This should not be used by the user.</b>
     * @param bot the bot.
     */
    public Event(JTABot bot) {
        this.bot = bot;
    }

    /**
     * Returns the specified bot.
     * @return the bot.
     */
    public JTABot getBot() {
        return bot;
    }

}
