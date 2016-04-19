package org.moab.eventlog;

import org.moab.events.MOABEvent;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Iterator;
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

    private int indexOfEvent(MOABEvent event) {
        Iterator<MOABEvent> iterator = iterator();
        MOABEvent myEvent;
        int c = 0;
        while (iterator.hasNext()) {
            myEvent = iterator.next();
            if (myEvent.getUUID().equals(event.getUUID())) {return c;};
            c++;
        }
        return -1;
    }

    private boolean hasEvent(MOABEvent event) {
        return indexOfEvent(event) != -1;
    }

    @Override
    public synchronized boolean add(MOABEvent moabEvent) {
        if (null == moabEvent.getUUID()) {
            moabEvent.setUUID(UUID.randomUUID());
            moabEvent.setCreated(ZonedDateTime.now(clock));
        }
        if (hasEvent(moabEvent)) { return false; }
        return super.add(moabEvent);
    }
}
