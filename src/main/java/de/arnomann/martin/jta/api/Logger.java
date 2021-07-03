package de.arnomann.martin.jta.api;

import de.arnomann.martin.jta.internal.util.Helpers;
import org.jetbrains.annotations.NotNull;

/**
 * A logger. It prints stuff to the console.
 */
public class Logger {

    private final Class<?> callerClass;

    /**
     * Creates a new logger.
     */
    public Logger() {
        Class<?> callerClassTemp;

        try {
            callerClassTemp = Class.forName(new Exception().getStackTrace()[1].getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            callerClassTemp = null;
        }

        this.callerClass = callerClassTemp;
    }

    /**
     * Sends an info message to the console.
     * @param msg The message to be written to the console.
     */
    public void info(String msg) {
        send(Level.INFO, msg);
    }

    /**
     * Sends a warning message to the console.
     * @param msg The message to be written to the console.
     */
    public void warn(String msg) {
        send(Level.WARNING, msg);
    }

    /**
     * Sends an error message to the console.
     * @param msg The message to be written to the console.
     */
    public void error(String msg) {
        send(Level.ERROR, msg);
    }

    /**
     * Sends an error message to the console.
     * @param msg The message to be written to the console.
     * @param e The cause of the error (an {@link java.lang.Exception} or {@link java.lang.Error})
     */
    public void error(String msg, @NotNull Throwable e) {
        send(Level.ERROR, msg);
        e.printStackTrace();
    }

    /**
     * Sends an fatal error message to the console. This will also stop the program.
     * @param msg The message to be written to the console.
     * @param exitCode The exit code.
     */
    public void fatal(String msg, int exitCode) {
        send(Level.FATAL, msg);
        System.exit(exitCode);
    }

    /**
     * Sends an fatal error message to the console. This will also stop the program.
     * @param msg The message to be written to the console.
     * @param e The cause of the fatal error (an {@link java.lang.Exception} or {@link java.lang.Error})
     * @param exitCode The exit code.
     */
    public void fatal(String msg, Throwable e, int exitCode) {
        send(Level.FATAL, msg);
        e.printStackTrace();
        System.exit(exitCode);
    }

    protected void send(Level level, String msg) {
        if(level.equals(Level.INFO) || level.equals(Level.WARNING)) {
            System.out.println(Helpers.format("[{} {}]: {}", callerClass.getName(), level.toString(), msg));
        } else {
            System.err.println(Helpers.format("[{} {}]: {}", callerClass.getName(), level.toString(), msg));
        }
    }

    public enum Level {
        INFO,
        WARNING,
        ERROR,
        FATAL
    }

}
