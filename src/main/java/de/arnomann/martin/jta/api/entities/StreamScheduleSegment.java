package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.requests.UpdateAction;

import java.time.LocalDateTime;

/**
 * Represents a stream schedule segment.
 * @see StreamSchedule
 */
public interface StreamScheduleSegment extends IDable<String> {

    /**
     * Returns the title of the segment.
     * @return the title.
     */
    String getTitle();

    /**
     * Returns if the segment is recurring.
     * @return if the segment is recurring.
     */
    boolean isRecurring();

    /**
     * Returns the schedule in which the segment is.
     * @return the schedule.
     */
    StreamSchedule getSchedule();

    /**
     * Returns when the stream is planned to start.
     * @return the start time.
     */
    LocalDateTime getStartTime();

    /**
     * Returns when the stream is planned to end.
     * @return the end time.
     */
    LocalDateTime getEndTime();

}
