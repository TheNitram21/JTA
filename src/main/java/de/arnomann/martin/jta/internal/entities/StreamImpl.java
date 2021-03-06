package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.AdLength;
import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.entities.Stream;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.ErrorResponse;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.api.util.TimeUtils;
import de.arnomann.martin.jta.internal.JTABotImpl;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.internal.util.Helpers;
import de.arnomann.martin.jta.internal.util.ResponseUtils;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class StreamImpl implements Stream {
    
    private final JTABotImpl bot;
    private final ChannelImpl streamer;
    private JSONObject json;
    
    public StreamImpl(JTABotImpl bot, ChannelImpl streamer, JSONObject json) {
        this.bot = bot;
        this.streamer = streamer;
        this.json = json;
    }
    
    @Override
    public ChannelImpl getChannel() {
        return streamer;
    }

    @Override
    public UpdateAction<Long> getViewers() {
        return new UpdateAction<>(this, () -> json.getLong("viewers"));
    }

    @Override
    public void update() {
        if(!streamer.isLive())
            throw new JTAException(Helpers.format("User {} has gone offline!", streamer.getUser().getName()));

        Response response = new Requester().request("https://api.twitch.tv/kraken/streams/" + streamer.getUser().getId(), this.bot
                .defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            this.json = json.getJSONObject("stream");
        } catch (IOException e) {
            throw new JTAException("Error while trying to read JSON of stream.", e);
        }
    }

    @Override
    public LocalDateTime getWhenStarted() {
        return TimeUtils.twitchTimeToLocalDateTime(json.getString("started_at"));
    }

    @Override
    public void startAd(AdLength length) {
        Map<String, String> headers = this.bot.defaultSetterHeaders(getChannel().getUser());
        headers.put("Content-Type", "application/json");

        Response response = new Requester(JTA.getClient()).post("https://api.twitch.tv/helix/channels/commercial", RequestBody.create(
                "{\"broadcaster_type\":\"" + getChannel().getUser().getId() + "\",\"length\":" + length.getLength() + "}", null),
                headers);

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));
        } catch (IOException | JSONException ignored) {
            // Ignore it.
        }
    }

    @Override
    public Long getId() {
        return json.getLong("_id");
    }

}
