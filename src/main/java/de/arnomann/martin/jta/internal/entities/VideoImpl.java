package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.entities.Video;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.ErrorResponse;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.api.util.TimeUtils;
import de.arnomann.martin.jta.internal.JTABotImpl;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.internal.util.ResponseUtils;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;

public class VideoImpl implements Video {

    private final JTABotImpl bot;
    private JSONObject json;
    private final UserImpl streamer;

    public VideoImpl(JTABotImpl bot, JSONObject json, UserImpl streamer) {
        this.bot = bot;
        this.json = json;
        this.streamer = streamer;
    }

    @Override
    public Long getId() {
        return json.getLong("id");
    }

    @Override
    public void update() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/videos?id=" + getId(), this.bot.defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if (ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            JSONArray jsonArrayData = json.getJSONArray("data");
            for (Object object : jsonArrayData) {
                this.json = (JSONObject) object;
            }
        } catch (IOException e) {
            throw new JTAException("Couldn't update video", e);
        }
    }

    @Override
    public UserImpl getStreamer() {
        return streamer;
    }

    @Override
    public UpdateAction<String> getTitle() {
        return new UpdateAction<>(this, () -> json.getString("title"));
    }

    @Override
    public UpdateAction<String> getDescription() {
        return new UpdateAction<>(this, () -> json.getString("description"));
    }

    @Override
    public UpdateAction<Long> getViews() {
        return new UpdateAction<>(this, () -> json.getLong("view_count"));
    }

    @Override
    public void delete() {
        Response response = new Requester(JTA.getClient()).delete("https://api.twitch.tv/helix/videos?id=" + getId(), null, this.bot
                .defaultSetterHeaders(getStreamer()));

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));
        } catch (IOException | JSONException ignored) {
            // Ignore it.
        }
    }

    @Override
    public LocalDateTime getCreationTime() {
        return TimeUtils.twitchTimeToLocalDateTime(json.getString("created_at"));
    }
}
