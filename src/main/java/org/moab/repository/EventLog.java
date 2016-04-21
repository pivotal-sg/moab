package org.moab.repository;

import org.moab.events.MOABEvent;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.List;
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

    public boolean add(MOABEvent moabEvent) {
        // New Event
        if (null == moabEvent.getUUID()) {
            moabEvent.setUUID(UUID.randomUUID());
            moabEvent.setCreated(ZonedDateTime.now(clock));
            return repo.add(moabEvent);
        }
        if (hasEvent(moabEvent)) {
            return false;
        }
        // Replay Event
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

    public int size() {
        return repo.size();
    }

    public List<MOABEvent> getAllForEventType(Class<?> eventClass) {
        Vector<MOABEvent> events = new Vector<MOABEvent>();
        for (MOABEvent anEvent: repo) {
            if (anEvent.getClass() == eventClass) {
                events.add(anEvent);
            }
        }
        return events;
    }
}
