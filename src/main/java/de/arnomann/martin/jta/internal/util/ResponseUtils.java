package de.arnomann.martin.jta.internal.util;

import org.json.JSONObject;

public final class ResponseUtils {

    private ResponseUtils() {}

    public static boolean isErrorResponse(JSONObject json) {
        return json.has("message") && json.has("status");
    }

}
