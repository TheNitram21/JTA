package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Channel;
import de.arnomann.martin.jta.api.entities.ChannelPointReward;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.ErrorResponse;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.internal.util.ResponseUtils;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;

public class ChannelPointRewardImpl implements ChannelPointReward {

    private final JTABot bot;
    private JSONObject json;
    private final Channel channel;

    public ChannelPointRewardImpl(JTABot bot, JSONObject json, Channel channel) {
        this.bot = bot;
        this.json = json;
        this.channel = channel;
    }

    @Override
    public Channel getChannel() {
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
    public void update() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/channel_points/custom_rewards?broadcaster_id=" +
                getChannel().getUser().getId() + "&id=" + getId(), this.bot.defaultSetterHeaders());

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
