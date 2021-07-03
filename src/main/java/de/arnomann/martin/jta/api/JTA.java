package de.arnomann.martin.jta.api;

import de.arnomann.martin.jta.api.util.Checks;
import okhttp3.OkHttpClient;

/**
 * Main class of the API.
 */
public class JTA {

    /**
     * Client used for HTTP-Requests.
     */
    private static OkHttpClient client;

    /**
     * If the API was initialized.
     */
    private static boolean initialized;

    /**
     * The logger of the API.
     */
    private static Logger logger;

    private JTA() {}

    /**
     * Initializes the API. <b>Without this, big parts of the API won't work.</b>
     */
    public static void initialize() {
        Checks.check(!isInitialized(), "API is already initialized!");

        client = new OkHttpClient();

        logger = new Logger();

        initialized = true;

        logger.info("Successfully initialized JTA.");
    }

    /**
     * Checks if the API was inizialized
     * @return <code>true</code> if {@link JTA#initialize()} was called before, otherwise false.
     */
    public static boolean isInitialized() {
        return initialized;
    }

    /**
     * Returns the OkHttpClient used for HTTP-Requests.
     * @return the client.
     */
    public static OkHttpClient getClient() {
        return client;
    }

    /**
     * Returns the logger.
     * @return the logger.
     */
    public static Logger getLogger() {
        return logger;
    }

}
