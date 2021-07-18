package de.arnomann.martin.jta.api.exceptions;

/**
 * Gets thrown when a check is false.
 */
public class ChecksException extends JTAException {

    /**
     * Creates a checks exception.
     * @param msg the message of the check.
     */
    public ChecksException(String msg) {
        super(msg);
    }

}
