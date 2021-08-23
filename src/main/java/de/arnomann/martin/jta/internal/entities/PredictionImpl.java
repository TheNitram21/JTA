package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.PredictionState;
import de.arnomann.martin.jta.api.entities.Prediction;
import de.arnomann.martin.jta.api.entities.User;
import org.json.JSONObject;

public class PredictionImpl implements Prediction {

    private final JTABot bot;
    private final JSONObject json;
    private final User streamer;

    public PredictionImpl(JTABot bot, JSONObject json, User streamer) {
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
    public String getOutcomeOne() {
        return json.getJSONArray("outcomes").getJSONObject(0).getString("title");
    }

    @Override
    public String getOutcomeTwo() {
        return json.getJSONArray("outcomes").getJSONObject(1).getString("title");
    }

    @Override
    public int getPredictionWindow() {
        return json.getInt("prediction_window");
    }

    @Override
    public PredictionState getState() {
        return PredictionState.valueOf(json.getString("status"));
    }

}
