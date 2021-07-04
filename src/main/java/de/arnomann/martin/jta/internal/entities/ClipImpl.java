package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Clip;
import de.arnomann.martin.jta.api.entities.User;
import de.arnomann.martin.jta.internal.JTAClass;
import org.json.JSONObject;

public class ClipImpl implements Clip, JTAClass {

    private final JTABot bot;
    private final JSONObject json;
    private final User creator;

    public ClipImpl(JTABot bot, JSONObject json, User creator) {
        this.bot = bot;
        this.json = json;
        this.creator = creator;
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
    public User getCreator() {
        return creator;
    }

    @Override
    public String getUrl() {
        return json.getString("url");
    }

}
