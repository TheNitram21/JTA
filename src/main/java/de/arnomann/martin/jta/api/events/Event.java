package de.arnomann.martin.jta.api.events;

import de.arnomann.martin.jta.api.JTABot;

public class Event {

    protected final JTABot bot;

    public Event(JTABot bot) {
        this.bot = bot;
    }

    public JTABot getBot() {
        return bot;
    }

}
