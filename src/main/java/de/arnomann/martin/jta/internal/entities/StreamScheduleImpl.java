package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.StreamSchedule;
import de.arnomann.martin.jta.api.entities.StreamScheduleSegment;
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
import java.util.ArrayList;
import java.util.List;

public class StreamScheduleImpl implements StreamSchedule {

    private final JTABot bot;
    private JSONObject json;
    private final User streamer;

    public StreamScheduleImpl(JTABot bot, JSONObject json, User streamer) {
        this.bot = bot;
        this.json = json;
        this.streamer = streamer;
    }

    @Override
    public UpdateAction<List<StreamScheduleSegment>> getSegments() {
        return new UpdateAction<>(this, () -> {
            List<StreamScheduleSegment> segments = new ArrayList<>();

            for(Object o : json.getJSONArray("segments")) {
                segments.add(new StreamScheduleSegmentImpl(this.bot, (JSONObject) o, this));
            }

            return segments;
        });
    }

    @Override
    public User getStreamer() {
        return streamer;
    }

    @Override
    public void update() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/schedule?broadcaster_id=" + getStreamer().getId(),
                null, this.bot.defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if (ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            this.json = json.getJSONObject("data");
        } catch (IOException e) {
            throw new JTAException("Couldn't update stream schedule", e);
        }
    }

}
