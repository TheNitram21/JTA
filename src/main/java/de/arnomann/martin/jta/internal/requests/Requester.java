package de.arnomann.martin.jta.internal.requests;

import de.arnomann.martin.jta.api.JTA;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

public final class Requester {

    private final OkHttpClient client;

    public Requester(OkHttpClient client) {
        this.client = client;
    }

    public Requester() {
        this(JTA.getClient());
    }

    @SafeVarargs
    public final Response request(String url, RequestBody body, Map<String, String>... headers) {
        Request.Builder requestBuilder = new Request.Builder().url(url);

        for (Map<String, String> map : headers) {
            map.forEach(requestBuilder::addHeader);
        }

        if(body != null)
            requestBuilder.post(body);
        else
            requestBuilder.get();

        Request request = requestBuilder.build();

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
