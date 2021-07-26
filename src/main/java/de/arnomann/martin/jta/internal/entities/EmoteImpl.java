package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Channel;
import de.arnomann.martin.jta.api.entities.Emote;
import org.json.JSONObject;

public class EmoteImpl implements Emote {

    private final JTABot bot;
    private final Channel channel;
    private JSONObject json;

    public EmoteImpl(JTABot bot, Channel channel, JSONObject json) {
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
    public Channel getChannel() {
        return isGlobal() ? null : channel;
    }

    @Override
    public long getId() {
        return json.getLong("id");
    }

}
