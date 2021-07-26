package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.StreamSchedule;
import de.arnomann.martin.jta.api.entities.StreamScheduleSegment;
import de.arnomann.martin.jta.api.util.TimeUtils;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class StreamScheduleSegmentImpl implements StreamScheduleSegment {

    private final JTABot bot;
    private JSONObject json;
    private final StreamSchedule schedule;

    public StreamScheduleSegmentImpl(JTABot bot, JSONObject json, StreamSchedule schedule) {
        this.bot = bot;
        this.json = json;
        this.schedule = schedule;
    }

    @Override
    public String getId() {
        return json.getString("id");
    }

    @Override
    public String getTitle() {
        return json.getString("title");
    }

    @Override
    public boolean isRecurring() {
        return json.getBoolean("is_recurring");
    }

    @Override
    public StreamSchedule getSchedule() {
        return schedule;
    }

    @Override
    public LocalDateTime getStartTime() {
        return TimeUtils.twitchTimeToLocalDateTime("start_time");
    }

    @Override
    public LocalDateTime getEndTime() {
        return TimeUtils.twitchTimeToLocalDateTime("end_time");
    }
}
