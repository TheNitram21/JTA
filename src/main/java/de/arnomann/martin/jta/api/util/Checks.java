package de.arnomann.martin.jta.api.util;

import de.arnomann.martin.jta.api.exceptions.ChecksException;
import de.arnomann.martin.jta.internal.util.Helpers;

import java.util.Collection;

/**
 * A simple class for checking some stuff. <b>This was heavily inspired by <a href="https://github.com/DV8FromTheWorld/JDA/blob/development/src/main/java/net/dv8tion/jda/internal/utils/Checks.java">JDA</a></b>
 */
public class Checks {

    /** To prevent from creating instances */
    private Checks() {}

    /**
     * Checks if <code>expression</code> is true.
     * @param expression the expression to check.
     * @param message the message of the exception which will be thrown if <code>expression</code> is false.
     * @throws ChecksException if <code>expression</code> is false.
     */
    public static void check(final boolean expression, final String message) {
        if(!expression) throw new ChecksException(message);
    }

    /**
     * Checks if <code>argument</code> is not null.
     * @param argument the argument to check.
     * @param name the name of the object.
     * @throws ChecksException if <code>argument</code> is null.
     * @see Checks#noneNull(Collection, String)
     */
    public static void notNull(final Object argument, final String name) {
        if(argument == null) throw new ChecksException(name + " may not be null!");
    }

    /**
     * Checks if <code>argument</code> is not null and doesn't contains <code>null</code> objects.
     * @param argument the argument to check.
     * @param name the name of the objects.
     * @throws ChecksException if <code>argument</code> is null or contains <code>null</code> objects.
     * @see Checks#notNull(Object, String)
     */
    public static void noneNull(final Collection<?> argument, final String name) {
        notNull(argument, name);
        argument.forEach(obj -> notNull(obj, name));
    }

    /**
     * Checks if <code>argument</code> is not empty.
     * @param argument the argument to check.
     * @param name the name of the object.
     * @throws ChecksException if <code>argument</code> is empty.
     * @see Checks#noneEmpty(Collection, String)
     */
    public static void notEmpty(final String argument, final String name) {
        notNull(argument, name);
        if(argument.isEmpty()) throw new ChecksException(name + " may not be empty!");
    }

    /**
     * Checks if <code>argument</code> not null and doesn't contains <code>null</code> and empty objects.
     * @param argument the argument to check.
     * @param name the name of the objects.
     * @throws ChecksException if <code>argument</code> contains empty objects.
     * @see Checks#notEmpty(String, String)
     */
    public static void noneEmpty(final Collection<String> argument, final String name) {
        noneNull(argument, name);
        argument.forEach(obj -> notEmpty(obj, name));
    }

    /**
     * Checks if <code>input</code> is not longer than <code>length</code>.
     * @param input the String to check.
     * @param length the maximal length.
     * @param name the name of the object which will be shown in the exception that will be thrown if <code>input</code> is longer than <code>length</code>.
     * @throws ChecksException if <code>input</code> is longer than <code>length</code>.
     */
    public static void notLonger(final String input, final int length, final String name) {
        notNull(input, name);
        check(input.length() <= length,
                Helpers.format("{} may not be longer than {} characters! Provided: \"{}\"", name, length, input));
    }

    /**
     * Checks if <code>input</code> is between <code>start</code> and <code>end</code>.
     * @param input the int to check.
     * @param start the minimal value.
     * @param end the maximal value.
     * @param name the name of the object.
     * @throws ChecksException if <code>input</code> is not between <code>start</code> and <code>end</code>.
     */
    public static void inRange(final int input, final int start, final int end, final String name) {
        check((start <= input && input <= end), Helpers.format("{} must be between {} and {}!", name, start, end));
    }

}
