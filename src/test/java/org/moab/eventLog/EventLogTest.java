package org.moab.eventLog;

import org.junit.Before;
import org.junit.Test;
import org.moab.eventlog.EventLog;
import org.moab.eventsource.AccountCreateEvent;
import org.moab.eventsource.MOABEvent;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;

public class EventLogTest {

    private Clock clock;
    private Instant instant;

    @Before
    public void setUp() {
        instant = Instant.now();
        clock = Clock.fixed(instant, ZoneId.systemDefault());
    }

    @Test
    public void pushEventToLog() {
        // MOABEvent is pushed to a log, and it has a UUID and Timestamp added
        AccountCreateEvent createEvent = new AccountCreateEvent();

        EventLog log = new EventLog(clock);

        assertThat(log.isEmpty()).isTrue();
        log.add(createEvent);
        assertThat(log.isEmpty()).isFalse();
        MOABEvent persisted = log.get(0);

        assertThat(persisted.getUUID()).isNotNull();
        assertThat(persisted.createdAt().toInstant()).isEqualTo(instant);
    }
}
