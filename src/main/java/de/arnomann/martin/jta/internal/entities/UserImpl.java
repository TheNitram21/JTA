package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Channel;
import de.arnomann.martin.jta.api.entities.Stream;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.api.util.EntityUtils;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.internal.util.Helpers;
import de.arnomann.martin.jta.api.entities.User;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserImpl implements User {

    private final JTABot bot;
    private JSONObject json;
    private final String name;

    public UserImpl(JSONObject json, JTABot bot) {
        this.bot = bot;
        this.json = json;
        this.name = json.getString("display_name");
    }

    @Override
    public void update() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/vnd.twitchtv.v5+json");
        headers.put("Client-ID", bot.getClientId());

        String nameToSearch = EntityUtils.userNameToId(this);

        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/kraken/users?login=" + nameToSearch, null, headers);
        try {
            JSONObject json = new JSONObject(response.body().string());
            JSONArray jsonArrayData = json.getJSONArray("users");
            if(jsonArrayData.getJSONObject(0).getString("display_name").equals(getName())) {
                this.json = jsonArrayData.getJSONObject(0);
            }
        } catch (JSONException | IOException e) { throw new JTAException("Can't fetch user.", e); }
    }

    @Override
    public long getId() {
        return json.getLong("id");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UpdateAction<String> getBio() {
        return new UpdateAction<>(this, () -> json.getString("bio"));
    }

    @Override
    public Channel getChannel() {
        Map<String, String> headers = new HashMap<>();
        headers.put("client-id", bot.getClientId());
        headers.put("Authorization", "Bearer " + bot.getToken());

        String nameToSearch = EntityUtils.userNameToId(this);

        Response response = new Requester(JTA.getClient()).request("https:///api.twitch.tv/helix/search/channels?query=" + nameToSearch, null, headers);
        try {
            JSONObject json = new JSONObject(response.body().string());
            JSONArray jsonArrayData = json.getJSONArray("data");
            for (Object object : jsonArrayData) {
                if(((JSONObject) object).getString("display_name").equals(getName())) {
                    return new ChannelImpl(bot, this, (JSONObject) object);
                }
            }
        } catch (JSONException | IOException e) { JTA.getLogger().error("Couldn't fetch channel of user " + getName()); }
        return null;
    }
}
