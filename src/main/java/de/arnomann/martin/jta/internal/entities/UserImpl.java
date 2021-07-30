package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.BroadcasterType;
import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.Channel;
import de.arnomann.martin.jta.api.entities.StreamSchedule;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.ErrorResponse;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.api.util.EntityUtils;
import de.arnomann.martin.jta.api.util.TimeUtils;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.api.entities.User;
import de.arnomann.martin.jta.internal.util.ResponseUtils;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserImpl implements User {

    private final JTABot bot;
    private JSONObject json;
    private final String name;

    public UserImpl(JSONObject json, JTABot bot) {
        this.bot = bot;
        this.json = json;
        this.name = json.getString("display_name");
    }

    @Override
    public void update() {
        String nameToSearch = EntityUtils.userNameToId(this);

        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/users?login=" + nameToSearch, this.bot
                .defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            JSONArray jsonArrayData = json.getJSONArray("ta");
            if(jsonArrayData.getJSONObject(0).getString("display_name").equals(getName())) {
                this.json = jsonArrayData.getJSONObject(0);
            }
        } catch (JSONException | IOException e) {
            throw new JTAException("Error while trying to read JSON of channel.", e);
        }
    }

    @Override
    public long getId() {
        return json.getLong("id");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Channel getChannel() {
        String nameToSearch = EntityUtils.userNameToId(this);

        Response response = new Requester(JTA.getClient()).request("https:///api.twitch.tv/helix/search/channels?query=" + nameToSearch,
            this.bot.defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            JSONArray jsonArrayData = json.getJSONArray("data");
            for (Object object : jsonArrayData) {
                if(((JSONObject) object).getString("display_name").equals(getName())) {
                    return new ChannelImpl(bot, this, (JSONObject) object);
                }
            }
        } catch (JSONException | IOException e) {
            throw new JTAException("Error while trying to read JSON of channel.", e);
        }
        return null;
    }

    @Override
    public UpdateAction<BroadcasterType> getBroadcasterType() {
        return new UpdateAction<>(this, () -> BroadcasterType.getByString(json.getString("broadcaster_type")));
    }

    @Override
    public StreamSchedule getStreamSchedule() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/schedule?broadcaster_id=" + getId(),
                this.bot.defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if (ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            return new StreamScheduleImpl(this.bot, json.getJSONObject("data"), this);
        } catch (IOException e) {
            throw new JTAException("Couldn't update stream schedule", e);
        }
    }

    @Override
    public List<Channel> getFollows() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/users/follows?first=100&from_id=" +
                getId(), this.bot.defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            List<Channel> list = new ArrayList<>();

            for(Object obj : json.getJSONArray("data"))
                list.add(this.bot.getUserByName(((JSONObject) obj).getString("to_name")).getChannel());

            return list;
        } catch (IOException e) {
            throw new JTAException("Error while trying to read JSON of follows.", e);
        }
    }

    @Override
    public List<User> getBlockedUsers() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/users/blocks?broadcaster_id=" +
                getId(), this.bot.defaultSetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            List<User> list = new ArrayList<>();

            for(Object obj : json.getJSONArray("data"))
                list.add(this.bot.getUserByName(((JSONObject) obj).getString("display_name")));

            return list;
        } catch (IOException e) {
            throw new JTAException("Error while trying to read JSON of follows.", e);
        }
    }

    @Override
    public LocalDateTime getCreationTime() {
        return TimeUtils.twitchTimeToLocalDateTime(json.getString("created_at"));
    }

    @Override
    public String toString() {
        return "User[" + getName() + "]";
    }
}
