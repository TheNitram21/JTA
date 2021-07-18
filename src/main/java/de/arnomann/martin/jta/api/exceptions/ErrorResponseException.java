package de.arnomann.martin.jta.api.exceptions;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.requests.ErrorResponse;

/**
 * Gets thrown when a request returned an error response.
 */
public class ErrorResponseException extends JTAException {
    private final ErrorResponse errorResponse;

    /**
     * Creates an error response exception.
     * @param errorResponse the error response.
     */
    public ErrorResponseException(ErrorResponse errorResponse) {
        super("A request returned an error.");
        this.errorResponse = errorResponse;
    }

    /**
     * Prints the code, the error and the message.
     */
    @Override
    public void printStackTrace() {
        JTA.getLogger().error("ERROR RESPONSE: " + errorResponse.getCode() + " " + errorResponse.getError() + " - " + errorResponse.getMessage());
    }

    /**
     * Returns the error response.
     * @return the error response.
     */
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

}
