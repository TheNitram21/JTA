package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Channel;
import de.arnomann.martin.jta.api.entities.Game;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.requests.ErrorResponse;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.internal.util.Helpers;
import de.arnomann.martin.jta.api.entities.Stream;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.internal.util.ResponseUtils;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StreamImpl implements Stream {
    
    private final JTABot bot;
    private final ChannelImpl streamer;
    private JSONObject json;
    
    public StreamImpl(JTABot bot, ChannelImpl streamer, JSONObject json) {
        this.bot = bot;
        this.streamer = streamer;
        this.json = json;
    }
    
    @Override
    public Channel getChannel() {
        return streamer;
    }

    @Override
    public UpdateAction<Long> getViewers() {
        return new UpdateAction<>(this, () -> json.getLong("viewers"));
    }

    @Override
    public void update() {
        if(!streamer.isLive().queue())
            throw new JTAException(Helpers.format("User {} has gone offline!", streamer.getUser().getName()));

        Map<String, String> headers = new HashMap<>();
        headers.put("Client-ID", bot.getClientId());
        headers.put("Authorization", "Bearer " + bot.getToken());

        Response response = new Requester().request("https://api.twitch.tv/kraken/streams/" + streamer.getUser().getId(), null, headers);

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
    public UpdateAction<Game> getGame() {
        return new UpdateAction<>(this, () -> {
            Game game = Game.getByName(json.getString("game"));
            if (game == null)
                throw new JTAException("Game is not implemented yet! Please contact the devs or create a pull request at the GitHub repository.");
            else
                return game;
        });
    }

    @Override
    public long getId() {
        return json.getLong("_id");
    }

}
