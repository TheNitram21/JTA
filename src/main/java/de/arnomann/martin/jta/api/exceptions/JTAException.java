package de.arnomann.martin.jta.api.exceptions;

import de.arnomann.martin.jta.api.JTA;

public class JTAException extends RuntimeException {

    public JTAException(String msg) {
        super(msg);
    }

    public JTAException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    public void printStackTrace() {
        JTA.getLogger().error("Exception in JTA. If this is an internal error, please contact the devs.");
        super.printStackTrace();
    }
}
