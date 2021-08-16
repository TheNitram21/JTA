package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.entities.HypeTrain;
import de.arnomann.martin.jta.api.entities.User;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.ErrorResponse;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.internal.JTABotImpl;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.internal.util.ResponseUtils;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class HypeTrainImpl implements HypeTrain {

    private final JTABotImpl bot;
    private final ChannelImpl channel;
    private JSONObject json;

    public HypeTrainImpl(JTABotImpl bot, ChannelImpl channel, JSONObject json) {
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
    public ChannelImpl getChannel() {
        return channel;
    }

    @Override
    public UpdateAction<User> getLastContributor() {
        return new UpdateAction<>(this, () -> bot.getUserById(json.getJSONObject("event_data").getJSONObject("last_contribution").getLong("user")));
    }

    @Override
    public void update() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/hypetrain/events?broadcaster_id=" + getChannel()
                        .getUser().getId(), this.bot.defaultGetterHeaders());

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
