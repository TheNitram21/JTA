package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.*;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.ErrorResponse;
import de.arnomann.martin.jta.api.requests.UpdateAction;
import de.arnomann.martin.jta.api.util.EntityUtils;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.internal.util.Helpers;
import de.arnomann.martin.jta.internal.util.ResponseUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChannelImpl implements Channel {

    private final JTABot bot;
    private final User user;
    private JSONObject json;

    public ChannelImpl(JTABot bot, User user, JSONObject json) {
        this.bot = bot;
        this.user = user;
        this.json = json;
    }

    @Override
    public ChatImpl getChat() {
        return new ChatImpl(this, bot);
    }

    @Override
    public UpdateAction<Stream> getStream() {
        return new UpdateAction<>(this, () -> {
            if (!isLive().queue())
                throw new JTAException(Helpers.format("Channel {} is not live!", getUser().getName()));

            Response response = new Requester().request("https://api.twitch.tv/kraken/streams/" + getUser().getId(), null, this.bot
                    .defaultGetterHeaders());

            try {
                JSONObject json = new JSONObject(response.body().string());

                if(ResponseUtils.isErrorResponse(json))
                    throw new ErrorResponseException(new ErrorResponse(json));

                return new StreamImpl(bot, this, json.getJSONObject("stream"));
            } catch (IOException e) {
                throw new JTAException("Error while trying to read stream JSON.", e);
            }
        });
    }

    @Override
    public UpdateAction<Boolean> isLive() {
        return new UpdateAction<>(this, () -> json.getBoolean("is_live"));
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public long getFollowerCount() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/kraken/channels/" + getId() + "/follows", null, this.bot
                .defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            return json.getLong("_total");
        } catch (IOException e) {
            throw new JTAException("Error while trying to read JSON of followers.", e);
        }
    }

    @Override
    public HypeTrain getHypeTrain() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/hypetrain/events?broadcaster_id=" + getUser().getId(),
                null, this.bot.defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            json = json.getJSONArray("data").getJSONObject(0);

            return new HypeTrainImpl(bot, this, json);
        } catch (IOException e) {
            throw new JTAException("Error while trying to read JSON of hype train.", e);
        }
    }

    @Override
    public TeamImpl getTeam() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/teams/channel?broadcaster_id=" + getUser().getId(),
                null, this.bot.defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            return (TeamImpl) bot.getTeamByName(json.getJSONArray("data").getJSONObject(0).getString("team_name"));
        } catch (IOException e) {
            throw new JTAException("Error while trying to read JSON of team.", e);
        }
    }

    @Override
    public List<ChatBadge> getChatBadges() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/chat/badges?broadcaster_id=" + getUser().getId(),
                null, this.bot.defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            List<ChatBadge> list = new ArrayList<>();

            for(Object obj : json.getJSONArray("data"))
                list.add(new ChatBadgeImpl(this.bot, (JSONObject) obj));

            return list;
        } catch (IOException e) {
            throw new JTAException("Error while trying to read JSON of chat badges.", e);
        }
    }

    @Override
    public List<Emote> getCustomEmotes() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/chat/emotes?broadcaster_id=" + getUser().getId(),
                null, this.bot.defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            List<Emote> list = new ArrayList<>();

            for(Object obj : json.getJSONArray("data"))
                list.add(new EmoteImpl(this.bot, this, (JSONObject) obj));

            return list;
        } catch (IOException e) {
            throw new JTAException("Error while trying to read JSON of channel emotes.", e);
        }
    }

    @Override
    public void update() {
        String nameToSearch = EntityUtils.userNameToId(user);

        Response response = new Requester(JTA.getClient()).request("https:///api.twitch.tv/helix/search/channels?query=" + nameToSearch, null,
                this.bot.defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if (ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            JSONArray jsonArrayData = json.getJSONArray("data");
            for (Object object : jsonArrayData) {
                if (((JSONObject) object).getString("display_name").equals(user.getName())) {
                    this.json = (JSONObject) object;
                }
            }
        } catch (JSONException | IOException e) {
            throw new JTAException("Couldn't update channel", e);
        }
    }

    @Override
    public long getId() {
        return json.getLong("id");
    }

    @Override
    public String toString() {
        return "Channel[" + user.getName() + "]";
    }
}
