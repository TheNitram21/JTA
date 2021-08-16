package de.arnomann.martin.jta.api.requests;

import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import org.json.JSONObject;

/**
 * Represents an error response.
 * @see ErrorResponseException
 */
public final class ErrorResponse {

    private final int code;
    private final String message;
    private final String error;

    /**
     * Creates a new error response.
     * @param code the code.
     * @param message the message.
     * @param error the error.
     */
    public ErrorResponse(int code, String message, String error) {
        this.code = code;
        this.message = message;
        this.error = error;
    }

    /**
     * Creates a new error response by the json.
     * @param json the json.
     */
    public ErrorResponse(JSONObject json) {
        this(json.getInt("status"), json.getString("message"), json.getString("error"));
    }

    /**
     * Returns the code of the error response.
     * @return the code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns the message of the error response.
     * @return the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the error of the error response.
     * @return the error.
     */
    public String getError() {
        return error;
    }

    /**
     * Converts the error response into a String representation.
     * @return the error response as a String.
     */
    @Override
    public String toString() {
        return getCode() + " " + getError() + " - " + getMessage();
    }

}
