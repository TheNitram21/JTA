package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Channel;
import de.arnomann.martin.jta.api.entities.Chat;
import de.arnomann.martin.jta.api.entities.Stream;
import de.arnomann.martin.jta.api.entities.User;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.api.util.EntityUtils;
import de.arnomann.martin.jta.internal.requests.Requester;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChannelImpl implements Channel {

    private final JTABot bot;
    private final User user;
    private JSONObject json;

    public ChannelImpl(JTABot bot, User user, JSONObject json) {
        this.bot = bot;
        this.user = user;
        this.json = json;
    }

    @Override
    public ChatImpl getChat() {
        return new ChatImpl(this, bot);
    }

    @Override
    public UpdateAction<Stream> getStream() {
        return null;
    }

    @Override
    public UpdateAction<Boolean> isLive() {
        return new UpdateAction<>(this, () -> json.getBoolean("is_live"));
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void update() {
        Map<String, String> headers = new HashMap<>();
        headers.put("client-id", bot.getClientId());
        headers.put("Authorization", "Bearer " + bot.getToken());

        String nameToSearch = EntityUtils.userNameToId(user);

        Response response = new Requester(JTA.getClient()).request("https:///api.twitch.tv/helix/search/channels?query=" + nameToSearch, null, headers);
        try {
            JSONObject json = new JSONObject(response.body().string());
            JSONArray jsonArrayData = json.getJSONArray("data");
            for (Object object : jsonArrayData) {
                if(((JSONObject) object).getString("display_name").equals(user.getName())) {
                    this.json = (JSONObject) object;
                }
            }
        } catch (JSONException | IOException e) { System.err.println("Couldn't update channel."); }
    }
}
