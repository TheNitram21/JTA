package de.arnomann.martin.jta.api.exceptions;

import de.arnomann.martin.jta.api.JTA;

/**
 * A general exception thrown when an error occurred in the API. This and subexceptions don't need to be caught!
 */
public class JTAException extends RuntimeException {

    /**
     * Creates a new JTAException.
     * @param msg the message.
     */
    public JTAException(String msg) {
        super(msg);
    }

    /**
     * Creates a new JTAException.
     * @param msg the message.
     * @param cause the cause.
     */
    public JTAException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Prints the exception stack trace.
     * @see Throwable#printStackTrace()
     */
    @Override
    public void printStackTrace() {
        JTA.getLogger().error("Exception in JTA. If this is an internal error, please contact the devs.");
        super.printStackTrace();
    }
}
