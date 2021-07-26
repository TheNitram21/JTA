package de.arnomann.martin.jta.api.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Does cool stuff with time.
 */
public class TimeUtils {

    private TimeUtils() {}

    /**
     * Converts a time twitch returns to a local date time object.
     * @param twitchTime the time to convert.
     * @return the converted local date time.
     */
    public static LocalDateTime twitchTimeToLocalDateTime(String twitchTime) {
        return LocalDateTime.parse(twitchTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'").withZone(ZoneId.of("UTC")));
    }

}
