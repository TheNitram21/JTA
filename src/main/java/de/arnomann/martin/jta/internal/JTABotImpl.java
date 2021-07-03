package de.arnomann.martin.jta.internal;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.internal.requests.Requester;
import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.events.Listener;
import de.arnomann.martin.jta.api.util.EntityUtils;
import de.arnomann.martin.jta.internal.entities.UserImpl;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JTABotImpl implements JTABot {

    private final String clientId;
    private final String clientSecret;
    private String accessToken = "";
    private long tokenExpiresWhen = 0L;

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
                Response response = new Requester(JTA.getClient()).request("https://id.twitch.tv/oauth2/token", RequestBody.create("client_id=" + clientId + "&client_secret=" + clientSecret
                        + "&grant_type=client_credentials", null));
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
        headers.put("client-id", getClientId());
        headers.put("Authorization", "Bearer " + getToken());

        String nameToSearch = EntityUtils.userNameToId(name);

        Response response = new Requester(JTA.getClient()).request("https:///api.twitch.tv/helix/search/channels?query=" + nameToSearch, null, headers);
        try {
            JSONObject json = new JSONObject(response.body().string());
            JSONArray jsonArrayData = json.getJSONArray("data");
            for (Object object : jsonArrayData) {
                if(((JSONObject) object).getString("broadcaster_login").equals(nameToSearch)) {
                    return new UserImpl((JSONObject) object, this);
                }
            }
        } catch (JSONException | IOException e) { System.err.println("No results."); }
        return null;
    }

    @Override
    public UserImpl getUserById(long id) {
        Map<String, String> headers = new HashMap<>();
        headers.put("client-id", getClientId());
        headers.put("Authorization", "Bearer " + getToken());

        Response response = new Requester(JTA.getClient()).request("https:///api.twitch.tv/helix/search/channels/" + id, null, headers);
        try {
            JSONObject json = new JSONObject(response.body().string());
            return new UserImpl((JSONObject) json, this);
        } catch (JSONException | IOException e) { System.err.println("No results."); }
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
        for(Listener l : more)
            EventHandler.removeEventListener(l);

        return this;
    }

}
