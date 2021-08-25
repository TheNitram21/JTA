package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.PollState;
import de.arnomann.martin.jta.api.entities.Poll;
import de.arnomann.martin.jta.api.entities.User;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PollImpl implements Poll {

    private final JTABot bot;
    private final JSONObject json;
    private final User streamer;

    public PollImpl(JTABot bot, JSONObject json, User streamer) {
        this.bot = bot;
        this.json = json;
        this.streamer = streamer;
    }

    @Override
    public String getId() {
        return json.getString("id");
    }

    @Override
    public User getStreamer() {
        return streamer;
    }

    @Override
    public String getTitle() {
        return json.getString("title");
    }

    @Override
    public List<String> getChoices() {
        List<String> choices = new ArrayList<>(5);

        for(Object obj : json.getJSONArray("choices"))
            choices.add(((JSONObject) obj).getString("title"));

        return choices;
    }

    @Override
    public PollState getState() {
        return PollState.valueOf(json.getString("status"));
    }

    @Override
    public int getDuration() {
        return json.getInt("duration");
    }

}
