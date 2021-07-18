package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.*;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.ErrorResponse;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.api.util.EntityUtils;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.internal.util.Helpers;
import de.arnomann.martin.jta.internal.util.ResponseUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        return new UpdateAction<>(this, () -> {
            if (!isLive().queue())
                throw new JTAException(Helpers.format("Channel {} is not live!", getUser().getName()));

            Map<String, String> headers = new HashMap<>();
            headers.put("Client-ID", bot.getClientId());
            headers.put("Authorization", "Bearer " + bot.getToken());

            Response response = new Requester().request("https://api.twitch.tv/kraken/streams/" + getUser().getId(), null, headers);

            try {
                JSONObject json = new JSONObject(response.body().string());

                if(ResponseUtils.isErrorResponse(json))
                    throw new ErrorResponseException(new ErrorResponse(json));

                return new StreamImpl(bot, this, json.getJSONObject("stream"));
            } catch (IOException e) {
                throw new JTAException("Error while trying to read stream JSON.", e);
            }
        });
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
    public long getFollowerCount() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Client-ID", bot.getClientId());
        headers.put("Authorization", "Bearer " + bot.getToken());

        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/kraken/channels/" + getId() + "/follows", null, headers);

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            return json.getLong("_total");
        } catch (IOException e) {
            throw new JTAException("Error while trying to read JSON of followers.", e);
        }
    }

    @Override
    public HypeTrain getHypeTrain() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + bot.getToken());
        headers.put("Client-ID", bot.getClientId());

        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/hypetrain/events?broadcaster_id=" + getUser().getId(),
                null, headers);

        try {
            JSONObject json = new JSONObject(response.body().string()).getJSONArray("data").getJSONObject(0);

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            return new HypeTrainImpl(bot, this, json);
        } catch (IOException e) {
            throw new JTAException("Error while trying to read JSON of hype train.", e);
        }
    }

    @Override
    public void update() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Client-ID", bot.getClientId());
        headers.put("Authorization", "Bearer " + bot.getToken());

        String nameToSearch = EntityUtils.userNameToId(user);

        Response response = new Requester(JTA.getClient()).request("https:///api.twitch.tv/helix/search/channels?query=" + nameToSearch, null,
                headers);

        try {
            JSONObject json = new JSONObject(response.body().string());

            if (ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            JSONArray jsonArrayData = json.getJSONArray("data");
            for (Object object : jsonArrayData) {
                if (((JSONObject) object).getString("display_name").equals(user.getName())) {
                    this.json = (JSONObject) object;
                }
            }
        } catch (JSONException | IOException e) {
            throw new JTAException("Couldn't update channel", e);
        }
    }

    @Override
    public long getId() {
        return json.getLong("id");
    }

}
