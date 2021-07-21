package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.entities.StreamSchedule;
import de.arnomann.martin.jta.api.entities.StreamScheduleSegment;
import org.json.JSONObject;

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
}
