package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Channel;
import de.arnomann.martin.jta.api.entities.HypeTrain;
import de.arnomann.martin.jta.api.entities.User;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.ErrorResponse;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.internal.util.ResponseUtils;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HypeTrainImpl implements HypeTrain {

    private final JTABot bot;
    private final Channel channel;
    private JSONObject json;

    public HypeTrainImpl(JTABot bot, Channel channel, JSONObject json) {
        this.bot = bot;
        this.channel = channel;
        this.json = json;
    }

    @Override
    public UpdateAction<HypeTrainState> getLevel() {
        return new UpdateAction<>(this, () -> HypeTrainState.getByInt(json.getJSONObject("event_data").getInt("level")));
    }

    @Override
    public UpdateAction<Integer> getPoints() {
        return new UpdateAction<>(this, () -> json.getJSONObject("event_data").getInt("total"));
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public UpdateAction<User> getLastContributor() {
        return new UpdateAction<>(this, () -> bot.getUserById(json.getJSONObject("event_data").getJSONObject("last_contribution").getLong("user")));
    }

    @Override
    public void update() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Client-ID", bot.getClientId());
        headers.put("Authorization", "Bearer " + bot.getToken());

        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/hypetrain/events?broadcaster_id=" + getChannel()
                        .getUser().getId(), null, headers);

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            this.json = json.getJSONArray("data").getJSONObject(0);
        } catch (IOException e) {
            throw new JTAException("Error while trying to read JSON of hype train.", e);
        }
    }

}
