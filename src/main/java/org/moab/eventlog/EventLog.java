package org.moab.eventlog;

import org.moab.events.MOABEvent;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.UUID;
import java.util.Vector;

@Component
public class EventLog {

    private Vector<MOABEvent> repo;
    private Clock clock;

    public EventLog() {
        this(Clock.systemDefaultZone());
    }

    public EventLog(Clock clock) {
        this.clock = clock;
        this.repo = new Vector<MOABEvent>();
    }

    private int indexOfEvent(MOABEvent event) {
        Iterator<MOABEvent> iterator = repo.iterator();
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

    public synchronized boolean add(MOABEvent moabEvent) {
        if (null == moabEvent.getUUID()) {
            moabEvent.setUUID(UUID.randomUUID());
            moabEvent.setCreated(ZonedDateTime.now(clock));
        }
        if (hasEvent(moabEvent)) { return false; }
        return repo.add(moabEvent);
    }

    public void clear() {
        repo.clear();
    }

    public boolean isEmpty() {
        return repo.isEmpty();
    }

    public MOABEvent get(int index) {
        return repo.get(index);
    }

    public synchronized int size() {
        return repo.size();
    }
}
