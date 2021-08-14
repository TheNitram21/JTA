package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Team;
import de.arnomann.martin.jta.api.entities.User;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.ErrorResponse;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.api.util.TimeUtils;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.internal.util.ResponseUtils;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class TeamImpl implements Team {

    private final JTABot bot;
    private JSONObject json;

    public TeamImpl(JTABot bot, JSONObject json) {
        this.bot = bot;
        this.json = json;
    }

    @Override
    public Long getId() {
        return json.getLong("id");
    }

    @Override
    public UpdateAction<List<User>> getMembers() {
        return new UpdateAction<>(this, () -> {
            List<User> members = new ArrayList<>();

            JSONArray jsonArray = this.json.getJSONArray("users");

            for(Object obj : jsonArray) {
                JSONObject userJson = (JSONObject) obj;
                members.add(this.bot.getUserByName(userJson.getString("user_name")));
            }

            return members;
        });
    }

    @Override
    public UpdateAction<String> getName() {
        return new UpdateAction<>(this, () -> json.getString("display_name"));
    }

    @Override
    public UpdateAction<String> getInfo() {
        return new UpdateAction<>(this, () -> json.getString("info"));
    }

    @Override
    public LocalDateTime getCreationTime() {
        return TimeUtils.twitchTimeToLocalDateTime(json.getString("created_at"));
    }

    @Override
    public void update() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/teams?name=" + json.getString("team_name"),
                this.bot.defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            if(json.getJSONArray("data").isEmpty())
                throw new JTAException("Couldn't update team!");

            this.json = json.getJSONArray("data").getJSONObject(0);
        } catch (IOException e) {
            throw new JTAException("Error while trying to read team JSON.", e);
        }
    }
}
