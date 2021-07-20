package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.User;
import de.arnomann.martin.jta.api.entities.Video;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.ErrorResponse;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.internal.util.ResponseUtils;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VideoImpl implements Video {

    private final JTABot bot;
    private JSONObject json;
    private final User streamer;

    public VideoImpl(JTABot bot, JSONObject json, User streamer) {
        this.bot = bot;
        this.json = json;
        this.streamer = streamer;
    }

    @Override
    public long getId() {
        return json.getLong("id");
    }

    @Override
    public void update() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + bot.getToken());
        headers.put("Client-ID", bot.getClientId());

        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/videos?id=" + getId(), null, headers);

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
    public User getStreamer() {
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
}
