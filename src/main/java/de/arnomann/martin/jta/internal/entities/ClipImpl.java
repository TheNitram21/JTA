package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.entities.Clip;
import de.arnomann.martin.jta.api.util.TimeUtils;
import de.arnomann.martin.jta.internal.JTABotImpl;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class ClipImpl implements Clip {

    private final JTABotImpl bot;
    private final JSONObject json;
    private final UserImpl creator;
    private final UserImpl channel;

    public ClipImpl(JTABotImpl bot, JSONObject json, UserImpl creator, UserImpl channel) {
        this.bot = bot;
        this.json = json;
        this.creator = creator;
        this.channel = channel;
    }

    @Override
    public String getSlug() {
        return json.getString("slug");
    }

    @Override
    public String getTitle() {
        return json.getString("title");
    }

    @Override
    public UserImpl getCreator() {
        return creator;
    }

    @Override
    public UserImpl getChannel() {
        return channel;
    }

    @Override
    public String getUrl() {
        return json.getString("url");
    }

    @Override
    public LocalDateTime getCreationTime() {
        return TimeUtils.twitchTimeToLocalDateTime(json.getString("created_at"));
    }

}
