package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.entities.ChatBadge;
import de.arnomann.martin.jta.internal.JTABotImpl;
import org.json.JSONObject;

public class ChatBadgeImpl implements ChatBadge {

    private final JTABotImpl bot;
    private final JSONObject json;

    public ChatBadgeImpl(JTABotImpl bot, JSONObject json) {
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
