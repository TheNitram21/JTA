package de.arnomann.martin.jta.internal.requests;

import de.arnomann.martin.jta.api.JTA;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

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

    public Response request(String url, RequestBody body, Map<String, String> headers) {
        Request.Builder requestBuilder = new Request.Builder().url(url);

        if(headers != null)
            headers.forEach(requestBuilder::addHeader);

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

    public Response delete(String url, RequestBody body, Map<String, String> headers) {
        Request.Builder requestBuilder = new Request.Builder().url(url);

        if(headers != null)
            headers.forEach(requestBuilder::addHeader);

        if(body != null)
            requestBuilder.delete(body);
        else
            requestBuilder.delete();

        Request request = requestBuilder.build();

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Response patch(String url, @NotNull RequestBody body, Map<String, String> headers) {
        Request.Builder requestBuilder = new Request.Builder().url(url);

        if(headers != null)
            headers.forEach(requestBuilder::addHeader);

        requestBuilder.patch(body);

        Request request = requestBuilder.build();

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Response put(String url, RequestBody body, Map<String, String> headers) {
        Request.Builder requestBuilder = new Request.Builder().url(url);

        if(headers != null)
            headers.forEach(requestBuilder::addHeader);

        requestBuilder.put(body);

        Request request = requestBuilder.build();

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
