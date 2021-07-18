package de.arnomann.martin.jta.api.requests;

import org.json.JSONObject;

public final class ErrorResponse {

    private final int code;
    private final String message;
    private final String error;

    public ErrorResponse(int code, String message, String error) {
        this.code = code;
        this.message = message;
        this.error = error;
    }

    public ErrorResponse(JSONObject json) {
        this(json.getInt("status"), json.getString("message"), json.getString("error"));
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return getCode() + " " + getMessage();
    }

}
