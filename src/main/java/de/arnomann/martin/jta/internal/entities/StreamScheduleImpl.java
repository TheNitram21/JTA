package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.entities.StreamSchedule;
import de.arnomann.martin.jta.api.entities.StreamScheduleSegment;
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
import java.util.ArrayList;
import java.util.List;

public class StreamScheduleImpl implements StreamSchedule {

    private final JTABotImpl bot;
    private JSONObject json;
    private final UserImpl streamer;

    public StreamScheduleImpl(JTABotImpl bot, JSONObject json, UserImpl streamer) {
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
    public UserImpl getStreamer() {
        return streamer;
    }

    @Override
    public void update() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/schedule?broadcaster_id=" + getStreamer().getId(),
                this.bot.defaultGetterHeaders());

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
