package de.arnomann.martin.jta.api.exceptions;

import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.requests.ErrorResponse;

public class ErrorResponseException extends JTAException {
    private final ErrorResponse errorResponse;

    public ErrorResponseException(ErrorResponse errorResponse) {
        super("A request returned an error.");
        this.errorResponse = errorResponse;
    }

    @Override
    public void printStackTrace() {
        JTA.getLogger().error(getMessage() + "\n" + errorResponse.toString());
    }
}
