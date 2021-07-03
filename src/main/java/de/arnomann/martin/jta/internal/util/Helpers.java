package de.arnomann.martin.jta.internal.util;

public class Helpers {

    private Helpers() {}

    public static String format(String s, Object... args) {
        for(Object o : args) {
           s = s.replaceFirst("\\{}", o.toString());
        }
        return s;
    }

}
