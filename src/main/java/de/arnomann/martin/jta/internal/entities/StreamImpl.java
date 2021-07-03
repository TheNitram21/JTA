package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Game;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.internal.util.Helpers;
import de.arnomann.martin.jta.api.entities.Stream;
import de.arnomann.martin.jta.api.entities.User;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.internal.JTAClass;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StreamImpl implements Stream, JTAClass {
    
    private final JTABot bot;
    private final UserImpl streamer;
    private JSONObject json;
    
    public StreamImpl(JTABot bot, UserImpl streamer, JSONObject json) {
        this.bot = bot;
        this.streamer = streamer;
        this.json = json;
    }
    
    @Override
    public User getUser() {
        return streamer;
    }

    @Override
    public UpdateAction<Long> getViewers() {
        return new UpdateAction<>(this, () -> json.getLong("viewers"));
    }

    @Override
    public void update() {
        if(!streamer.isLive().queue())
            throw new JTAException(Helpers.format("User {} has gone offline!", streamer.getName()));

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/vnd.twitchtv.v5+json");
        headers.put("Client-ID", bot.getClientId());

        Response respone = new Requester().request("https://api.twitch.tv/kraken/streams/" + streamer.getId(), null, headers);

        try {
            JSONObject json = new JSONObject(respone.body().string());
            this.json = json.getJSONObject("stream");
        } catch (IOException e) {
            throw new JTAException("Error while trying to read stream JSON");
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

}
