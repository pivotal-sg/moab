package org.moab.repository;

import org.junit.Before;
import org.junit.Test;
import org.moab.events.AccountCreated;
import org.moab.events.MOABEvent;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

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
        AccountCreated createEvent = new AccountCreated();

        EventLog log = new EventLog(clock);

        assertThat(log.isEmpty()).isTrue();
        boolean applied = log.add(createEvent);
        assertThat(applied).isTrue();
        assertThat(log.isEmpty()).isFalse();
        MOABEvent persisted = log.get(0);

        assertThat(persisted.getUUID()).isNotNull();
        assertThat(persisted.createdAt().toInstant()).isEqualTo(instant);
    }

    @Test
    public void DoesNotPersistSameEvent() {
        AccountCreated createEvent = new AccountCreated();

        EventLog log = new EventLog(clock);

        assertThat(log.isEmpty()).isTrue();
        assertThat(log.add(createEvent)).isTrue();
        assertThat(log.size()).isEqualTo(1);
        assertThat(log.add(createEvent)).isFalse();
        assertThat(log.size()).isEqualTo(1);
    }
}
