package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.entities.StreamScheduleSegment;
import de.arnomann.martin.jta.api.util.TimeUtils;
import de.arnomann.martin.jta.internal.JTABotImpl;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class StreamScheduleSegmentImpl implements StreamScheduleSegment {

    private final JTABotImpl bot;
    private JSONObject json;
    private final StreamScheduleImpl schedule;

    public StreamScheduleSegmentImpl(JTABotImpl bot, JSONObject json, StreamScheduleImpl schedule) {
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
    public StreamScheduleImpl getSchedule() {
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
