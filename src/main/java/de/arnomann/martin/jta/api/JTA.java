package de.arnomann.martin.jta.api;

import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import de.arnomann.martin.jta.api.util.Checks;
import okhttp3.OkHttpClient;

/**
 * Main class of the API.
 */
public final class JTA {

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

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            if (e instanceof ErrorResponseException) ((ErrorResponseException) e).printStackTrace();
            else e.printStackTrace();
        });

        logger.info("Successfully initialized JTA.");

        initialized = true;
    }

    /**
     * Checks if the API was initialized
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
