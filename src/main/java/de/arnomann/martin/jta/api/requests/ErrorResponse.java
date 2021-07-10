package de.arnomann.martin.jta.api.requests;

public enum ErrorResponse {
    REQUEST_NOT_VALID(          400, "Something is wrong with the request."),
    UNAUTHORIZED(               401, "The OAuth token does not have the correct scope or does not have the required permission on behalf of the specified user."),
    FORBIDDEN(                  403, "This usually indicates that authentication was provided, but the authenticated user is not permitted to perform the requested operation. For example, a user who is not a partner might have tried to start a commercial."),
    NOT_FOUND(                  404, "For example, the channel, user, or relationship could not be found."),
    UNPROCESSABLE_ENTITY(       422, "For example, for a user subscription endpoint, the specified channel does not have a subscription program."),
    TOO_MANY_REQUESTS(          429, "Too many requests were sent."),
    INTERNAL_SERVER_ERROR(      500, "An error occured in the twitch servers."),
    SERVICE_UNAVAILABLE(        503, "For example, the status of a game or ingest server cannot be retrieved.");

    private final int code;
    private final String explanation;

    ErrorResponse(int code, String explanation) {
        this.code = code;
        this.explanation = explanation;
    }

    public int getCode() {
        return code;
    }

    public String getExplanation() {
        return explanation;
    }

    @Override
    public String toString() {
        return getCode() + " " + getExplanation();
    }
}
