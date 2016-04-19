package org.moab.eventlog;

import org.moab.eventsource.MOABEvent;

import java.awt.*;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.UUID;
import java.util.Vector;

public class EventLog extends Vector<MOABEvent> {

    private Clock clock;

    public EventLog() {
        this(Clock.systemDefaultZone());
    }

    public EventLog(Clock clock) {
        this.clock = clock;
    }

    @Override
    public synchronized boolean add(MOABEvent moabEvent) {
        moabEvent.setUUID(UUID.randomUUID());
        moabEvent.setCreated(ZonedDateTime.now(clock));
        return super.add(moabEvent);
    }
}
