package de.arnomann.martin.jta.internal;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.Permission;
import de.arnomann.martin.jta.api.entities.ChatBadge;
import de.arnomann.martin.jta.api.entities.Emote;
import de.arnomann.martin.jta.api.entities.User;
import de.arnomann.martin.jta.api.entities.Video;
import de.arnomann.martin.jta.api.events.Listener;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.ErrorResponse;
import de.arnomann.martin.jta.api.util.Checks;
import de.arnomann.martin.jta.api.util.EntityUtils;
import de.arnomann.martin.jta.internal.entities.*;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.internal.util.Helpers;
import de.arnomann.martin.jta.internal.util.ResponseUtils;
import okhttp3.OkHttp;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class JTABotImpl implements JTABot {

    private final String clientId;
    private final String clientSecret;
    private String accessToken = "";
    private long tokenExpiresWhen = -1L;
    private String redirectUri = "http://localhost";
    private String oAuthToken = "";
    private final List<Permission> neededPermissions = new ArrayList<>();
    private final Map<Long, String> userAccessTokens = new HashMap<>();

    public JTABotImpl(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public boolean isTokenValid() {
        return tokenExpiresWhen >= System.currentTimeMillis() + 10000;
    }

    @Override
    public String getToken() {
        if(!isTokenValid()) {
            try {
                StringBuilder sb = new StringBuilder();

                for(int i = 0; i < neededPermissions.size(); i++) {
                    sb.append(neededPermissions.get(i));
                    if((i + 1) != neededPermissions.size())
                        sb.append(" ");
                }

                Response response = new Requester(JTA.getClient()).post("https://id.twitch.tv/oauth2/token", RequestBody.create(
                        "client_id=" + clientId + "&client_secret=" + clientSecret + "&grant_type=client_credentials&scopes=" + sb,
                        null), null);
                accessToken = new JSONObject(response.body().string()).getString("access_token");
                tokenExpiresWhen = System.currentTimeMillis() + 10000;
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return accessToken;
    }

    @Override
    public URL getUserAccessTokenLink() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < neededPermissions.size(); i++) {
            sb.append(neededPermissions.get(i).toTwitchScope());
            if((i + 1) != neededPermissions.size())
                sb.append(" ");
        }

        String BASE_LINK = "https://id.twitch.tv/oauth2/authorize?client_id={}&redirect_uri={}&response_type=token&scope={}";

        try {
            return new URL(Helpers.format(BASE_LINK, getClientId(), redirectUri, sb.toString()));
        } catch (MalformedURLException ignored) {
            // WILL NEVER HAPPEN
            return null;
        }
    }

    @Override
    public void setUserAccessToken(User user, String userToken) {
        Checks.notNull(user, "User");
        Checks.notEmpty(userToken, "User Access Token");
        userAccessTokens.put(user.getId(), userToken);
    }

    @Override
    public void setUserAccessToken(Map<User, String> tokens) {
        Checks.notNull(tokens, "User Access Tokens");

        tokens.forEach((user, token) -> userAccessTokens.put(user.getId(), token));
    }

    @Override
    public String getUserAccessToken(User user) {
        return userAccessTokens.get(user.getId());
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public void setRedirectUri(String redirectUri) {
        if(!redirectUri.isBlank()) this.redirectUri = redirectUri;
    }

    @Override
    public Map<String, String> defaultGetterHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        headers.put("Client-ID", getClientId());
        return headers;
    }

    @Override
    public Map<String, String> defaultSetterHeaders(User user) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getUserAccessToken(user));
        headers.put("Client-ID", getClientId());
        return headers;
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    @Override
    public UserImpl getUserByName(String name) {
        String nameToSearch = EntityUtils.userNameToId(name);

        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/users?login=" + nameToSearch, this
                .defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            if(json.getJSONArray("data").isEmpty())
                throw new JTAException("No user with name " + name + " found!");

            JSONArray jsonArrayData = json.getJSONArray("data");
            if(jsonArrayData.getJSONObject(0).getString("display_name").equals(name)) {
                return new UserImpl(jsonArrayData.getJSONObject(0), this);
            }
        } catch (JSONException | IOException e) {
            throw new JTAException("Error while reading JSON", e);
        }
        return null;
    }

    @Override
    public UserImpl getUserById(long id) {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/users?id=" + id, this
                .defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            if(json.getJSONArray("data").isEmpty())
                throw new JTAException("No user with id " + id + " found!");

            return new UserImpl(json, this);
        } catch (JSONException | IOException e) {
            JTA.getLogger().error(Helpers.format("No results: No user with id {} found.", id));
        }
        return null;
    }

    @Override
    public TeamImpl getTeamByName(String name) {
        String nameToSearch = EntityUtils.teamNameToId(name);

        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/teams?name=" + nameToSearch, this
                .defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            if(json.getJSONArray("data").isEmpty())
                throw new JTAException("Couldn't get team!");

            return new TeamImpl(this, json.getJSONArray("data").getJSONObject(0));
        } catch (IOException e) {
            throw new JTAException("Error while trying to read team JSON.", e);
        }
    }

    @Override
    public TeamImpl getTeamById(long id) {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/teams?id=" + id, this
                .defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            if(json.getJSONArray("data").isEmpty())
                throw new JTAException("Couldn't get team!");

            return new TeamImpl(this, json.getJSONArray("data").getJSONObject(0));
        } catch (IOException e) {
            throw new JTAException("Error while trying to read team JSON.", e);
        }
    }

    @Override
    public VideoImpl getVideoById(long id) {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/videos?id=" + id, this
                .defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if (ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            return new VideoImpl(this, json.getJSONArray("data").getJSONObject(0), getUserByName(json.getJSONArray("data")
                    .getJSONObject(0).getString("user_name")));
        } catch (IOException e) {
            throw new JTAException("Couldn't update video", e);
        }
    }

    @Override
    public JTABotImpl registerEventListeners(Listener first, Listener... more) {
        EventHandler.registerEventListener(first);
        for(Listener l : more)
            EventHandler.registerEventListener(l);

        return this;
    }

    @Override
    public JTABotImpl removeEventListeners(Listener first, Listener... more) {
        EventHandler.removeEventListener(first);
        for (Listener l : more)
            EventHandler.removeEventListener(l);

        return this;
    }

    @Override
    public void setChatOAuthToken(String token) {
        Checks.notEmpty(token, "OAuth token");

        this.oAuthToken = token;
    }

    @Override
    public String getChatOAuthToken() {
        return this.oAuthToken;
    }

    @Override
    public ClipImpl getClipBySlug(String slug) {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/clips?id=" + slug, this
                .defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            return new ClipImpl(this, json, getUserByName(json.getJSONObject("curator").getString("name")),
                    getUserByName(json.getJSONObject("broadcaster").getString("name")));
        } catch (JSONException | IOException e) {
            JTA.getLogger().error(Helpers.format("No results: No clip with slug {} found.", slug));
        }
        return null;
    }

    @Override
    public void addNeededPermissions(EnumSet<Permission> permissions) {
        neededPermissions.addAll(permissions);
    }

    @Override
    public void removeNeededPermissions(EnumSet<Permission> permissions) {
        neededPermissions.removeAll(permissions);
    }

    @Override
    public List<ChatBadge> getGlobalChatBadges() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/chat/badges/global", this
                .defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            List<ChatBadge> list = new ArrayList<>();

            for(Object obj : json.getJSONArray("data"))
                list.add(new ChatBadgeImpl(this, (JSONObject) obj));

            return list;
        } catch (IOException e) {
            throw new JTAException("Error while trying to read JSON of chat badges.", e);
        }
    }

    @Override
    public List<Emote> getGlobalEmotes() {
        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/chat/emotes/global", this
                .defaultGetterHeaders());

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            List<Emote> list = new ArrayList<>();

            for(Object obj : json.getJSONArray("data"))
                list.add(new EmoteImpl(this, null, (JSONObject) obj));

            return list;
        } catch (IOException e) {
            throw new JTAException("Error while trying to read JSON of chat badges.", e);
        }
    }

}
