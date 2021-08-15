package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.entities.Emote;
import de.arnomann.martin.jta.internal.JTABotImpl;
import org.json.JSONObject;

public class EmoteImpl implements Emote {

    private final JTABotImpl bot;
    private final ChannelImpl channel;
    private JSONObject json;

    public EmoteImpl(JTABotImpl bot, ChannelImpl channel, JSONObject json) {
        this.bot = bot;
        this.channel = channel;
        this.json = json;
    }

    @Override
    public String getName() {
        return json.getString("name");
    }

    @Override
    public String getImageUrl() {
        return json.getJSONObject("images").getString("url_1x");
    }

    @Override
    public boolean isGlobal() {
        return !json.has("emote_type");
    }

    @Override
    public ChannelImpl getChannel() {
        return isGlobal() ? null : channel;
    }

    @Override
    public Long getId() {
        return json.getLong("id");
    }

}
