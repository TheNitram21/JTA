package de.arnomann.martin.jta.internal;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.Permission;
import de.arnomann.martin.jta.api.events.Listener;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.exceptions.JTAException;
import de.arnomann.martin.jta.api.requests.ErrorResponse;
import de.arnomann.martin.jta.api.util.Checks;
import de.arnomann.martin.jta.api.util.EntityUtils;
import de.arnomann.martin.jta.internal.entities.ClipImpl;
import de.arnomann.martin.jta.internal.entities.UserImpl;
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
import java.util.*;

public class JTABotImpl implements JTABot {

    private final String clientId;
    private final String clientSecret;
    private String accessToken = "";
    private long tokenExpiresWhen = 0L;
    private String oAuthToken = "";
    private final List<Permission> neededPermissions = new ArrayList<>();

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

                Response response = new Requester(JTA.getClient()).request("https://id.twitch.tv/oauth2/token", RequestBody.create("client_id=" + clientId + "&client_secret=" + clientSecret
                        + "&grant_type=client_credentials&scopes=" + sb, null), null);
                accessToken = new JSONObject(response.body().string()).getString("access_token");
                tokenExpiresWhen = System.currentTimeMillis() + 10000;
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return accessToken;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    @Override
    public UserImpl getUserByName(String name) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Client-ID", getClientId());
        headers.put("Authorization", "Bearer " + getToken());

        String nameToSearch = EntityUtils.userNameToId(name);

        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/users?login=" + nameToSearch, null, headers);

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

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
        Map<String, String> headers = new HashMap<>();
        headers.put("Client-ID", getClientId());
        headers.put("Authorization", "Bearer " + getToken());

        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/search/channels/" + id, null, headers);

        try {
            JSONObject json = new JSONObject(response.body().string());

            if(ResponseUtils.isErrorResponse(json))
                throw new ErrorResponseException(new ErrorResponse(json));

            return new UserImpl(json, this);
        } catch (JSONException | IOException e) {
            JTA.getLogger().error(Helpers.format("No results: No user with id {} found.", id));
        }
        return null;
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
        Map<String, String> headers = new HashMap<>();
        headers.put("Client-ID", getClientId());
        headers.put("Authorization", "Bearer " + getToken());

        Response response = new Requester(JTA.getClient()).request("https://api.twitch.tv/helix/clips?id=" + slug, null, headers);

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

}
