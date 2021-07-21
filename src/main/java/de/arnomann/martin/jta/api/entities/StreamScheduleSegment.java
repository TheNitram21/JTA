package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.requests.UpdateAction;

/**
 * Represents a stream schedule segment.
 * @see StreamSchedule
 */
public interface StreamScheduleSegment {

    /**
     * Returns the segment id.
     * @return the id.
     */
    String getId();

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

}
