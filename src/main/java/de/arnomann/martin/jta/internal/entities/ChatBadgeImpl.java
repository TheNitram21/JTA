package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.ChatBadge;
import org.json.JSONObject;

public class ChatBadgeImpl implements ChatBadge {

    private final JTABot bot;
    private JSONObject json;

    public ChatBadgeImpl(JTABot bot, JSONObject json) {
        this.bot = bot;
        this.json = json;
    }

    @Override
    public String getId() {
        return json.getString("set_id");
    }

    @Override
    public String getImageUrl() {
        return json.getJSONArray("versions").getJSONObject(0).getString("image_url_1x");
    }

}
