package de.arnomann.martin.jta.api.entities;

import de.arnomann.martin.jta.api.requests.UpdateAction;

import java.util.List;

/**
 * Represents a stream schedule.
 */
public interface StreamSchedule extends Updatable {

    /**
     * Returns the stream schedule segments of the schedule.
     * @return the segments.
     */
    UpdateAction<List<StreamScheduleSegment>> getSegments();

    /**
     * Returns the streamer.
     * @return the streamer.
     */
    User getStreamer();

}
