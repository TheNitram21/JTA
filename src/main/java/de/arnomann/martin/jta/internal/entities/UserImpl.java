package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Stream;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.api.util.EntityUtils;
import de.arnomann.martin.jta.internal.JTAClass;
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

public class UserImpl implements User, JTAClass {

    private final JTABot bot;
    private JSONObject json;
    private final String name;
    private MessageSenderBot msgbot;

    public UserImpl(JSONObject json, JTABot bot) {
        this.bot = bot;
        this.json = json;
        this.name = json.getString("display_name");
    }

    @Override
    public void update() {
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
                    this.json = (JSONObject) object;
                }
            }
        } catch (JSONException | IOException e) { System.err.println("Couldn't update user."); }
    }

    @Override
    public long getId() {
        return json.getLong("id");
    }

    @Override
    public UpdateAction<Boolean> isLive() {
        return new UpdateAction<>(this, () -> json.getBoolean("is_live"));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ChatImpl getChat() {
        return new ChatImpl(this, bot);
    }

    @Override
    public UpdateAction<Stream> getStream() {
        return new UpdateAction<>(this, () -> {
            if (!isLive().queue())
                throw new JTAException(Helpers.format("User {} is not live!", getName()));

            Map<String, String> headers = new HashMap<>();
            headers.put("Accept", "application/vnd.twitchtv.v5+json");
            headers.put("Client-ID", bot.getClientId());

            Response respone = new Requester().request("https://api.twitch.tv/kraken/streams/" + getId(), null, headers);

            try {
                JSONObject json = new JSONObject(respone.body().string());
                return new StreamImpl(bot, this, json.getJSONObject("stream"));
            } catch (IOException e) {
                throw new JTAException("Error while trying to read stream JSON");
            }
        });
    }

    @Override
    public UpdateAction<String> getBio() {
        return new UpdateAction<String>(this, () -> json.getString("bio"));
    }

}
