package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.BroadcasterType;
import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.UserType;
import de.arnomann.martin.jta.api.entities.Channel;
import de.arnomann.martin.jta.api.entities.User;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.ErrorResponse;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.api.util.EntityUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserImpl implements User {

    private final JTABotImpl bot;
    private JSONObject json;
    private final String name;

    public UserImpl(JSONObject json, JTABotImpl bot) {
        this.bot = bot;
        this.json = json;
        this.name = json.getString("display_name");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserImpl user = (UserImpl) o;

        return Objects.equals(name, user.name) && getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
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

            JSONArray jsonArrayData = json.getJSONArray("data");
            if(jsonArrayData.getJSONObject(0).getString("display_name").equals(getName())) {
                this.json = jsonArrayData.getJSONObject(0);
            }
        } catch (JSONException | IOException e) {
            throw new JTAException("Error while trying to update JSON of user.", e);
        }
    }

    @Override
    public Long getId() {
        return json.getLong("id");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ChannelImpl getChannel() {
        Response response = new Requester(JTA.getClient()).request("https:///api.twitch.tv/helix/channels?broadcaster_id=" + getId(),
            this.bot.defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            JSONArray jsonArrayData = json.getJSONArray("data");
            for (Object object : jsonArrayData) {
                if(((JSONObject) object).getString("broadcaster_name").equals(getName())) {
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
    public UpdateAction<UserType> getUserType() {
        return new UpdateAction<>(this, () -> UserType.getByString(json.getString("type")));
    }

    @Override
    public StreamScheduleImpl getStreamSchedule() {
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
    public boolean isFollowing(Channel channel) {
        return getFollows().contains(channel);
    }

    @Override
    public List<User> getBlockedUsers() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/users/blocks?broadcaster_id=" +
                getId(), this.bot.defaultSetterHeaders(this));

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            List<User> list = new ArrayList<>();

            for(Object obj : json.getJSONArray("data"))
                list.add(this.bot.getUserByName(((JSONObject) obj).getString("display_name")));

            return list;
        } catch (IOException e) {
            throw new JTAException("Error while trying to read JSON of blocks.", e);
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
