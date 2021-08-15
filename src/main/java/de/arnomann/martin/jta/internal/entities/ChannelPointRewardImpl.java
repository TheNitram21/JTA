package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.entities.ChannelPointReward;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.ErrorResponse;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.internal.JTABotImpl;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.internal.util.ResponseUtils;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class ChannelPointRewardImpl implements ChannelPointReward {

    private final JTABotImpl bot;
    private JSONObject json;
    private final ChannelImpl channel;

    public ChannelPointRewardImpl(JTABotImpl bot, JSONObject json, ChannelImpl channel) {
        this.bot = bot;
        this.json = json;
        this.channel = channel;
    }

    @Override
    public ChannelImpl getChannel() {
        return channel;
    }

    @Override
    public String getId() {
        return json.getString("id");
    }

    @Override
    public UpdateAction<Color> getBackgroundColor() {
        return new UpdateAction<>(this, () -> new Color(Integer.parseInt(json.getString("background_color").substring(1))));
    }

    @Override
    public UpdateAction<Boolean> isEnabled() {
        return new UpdateAction<>(this, () -> json.getBoolean("is_enabled"));
    }

    @Override
    public UpdateAction<Integer> getCost() {
        return new UpdateAction<>(this, () -> json.getInt("cost"));
    }

    @Override
    public UpdateAction<String> getTitle() {
        return new UpdateAction<>(this, () -> json.getString("title"));
    }

    @Override
    public void setBackgroundColor(Color color) {
        Map<String, String> headers = this.bot.defaultSetterHeaders(getChannel().getUser());
        headers.put("Content-Type", "application/json");

        Response response = new Requester(JTA.getClient()).patch("https://api.twitch.tv/helix/channel_points/custom_rewards?broadcaster_id=" +
                getChannel().getUser().getId() + "&id=" + getId(), RequestBody.create("{ \"color\":\"" + String.format("#%02X%02X%02X",
                color.getRed(), color.getGreen(), color.getBlue()) + "\" }", null), headers);

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));
        } catch (IOException e) {
            // IGNORE IT
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        Map<String, String> headers = this.bot.defaultSetterHeaders(getChannel().getUser());
        headers.put("Content-Type", "application/json");

        Response response = new Requester(JTA.getClient()).patch("https://api.twitch.tv/helix/channel_points/custom_rewards?broadcaster_id=" +
                getChannel().getUser().getId() + "&id=" + getId(), RequestBody.create("{ \"is_enabled\":\"" + enabled + "\" }",
                null), headers);

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));
        } catch (IOException e) {
            // IGNORE IT
        }
    }

    @Override
    public void setCost(int cost) {
        Map<String, String> headers = this.bot.defaultSetterHeaders(getChannel().getUser());
        headers.put("Content-Type", "application/json");

        Response response = new Requester(JTA.getClient()).patch("https://api.twitch.tv/helix/channel_points/custom_rewards?broadcaster_id=" +
                getChannel().getUser().getId() + "&id=" + getId(), RequestBody.create("{ \"cost\":\"" + cost + "\" }",
                null), headers);

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));
        } catch (IOException e) {
            // IGNORE IT
        }
    }

    @Override
    public void setTitle(String title) {
        Map<String, String> headers = this.bot.defaultSetterHeaders(getChannel().getUser());
        headers.put("Content-Type", "application/json");

        Response response = new Requester(JTA.getClient()).patch("https://api.twitch.tv/helix/channel_points/custom_rewards?broadcaster_id=" +
                getChannel().getUser().getId() + "&id=" + getId(), RequestBody.create("{ \"title\":\"" + title + "\" }",
                null), headers);

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));
        } catch (IOException e) {
            // IGNORE IT
        }
    }

    @Override
    public void delete() {
        Response response = new Requester(JTA.getClient()).delete("https://api.twitch.tv/helix/channel_points/custom_rewards?broadcaster_id=" +
                getChannel().getUser().getId() + "&id=" + getId(), null, this.bot.defaultSetterHeaders(getChannel().getUser()));

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));
        } catch (IOException e) {
            // IGNORE IT: Usually, there is no response
        }
    }

    @Override
    public void update() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/channel_points/custom_rewards?broadcaster_id=" +
                getChannel().getUser().getId() + "&id=" + getId(), this.bot.defaultSetterHeaders(getChannel().getUser()));

        try {
            JSONObject json = new JSONObject(response.body().string());

            if (ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            JSONArray jsonArrayData = json.getJSONArray("data");
            this.json = jsonArrayData.getJSONObject(0);
        } catch (IOException e) {
            throw new JTAException("Couldn't update channel point reward.", e);
        }
    }
}
