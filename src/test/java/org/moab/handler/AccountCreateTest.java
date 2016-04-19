package org.moab.handler;

import org.junit.Test;
import org.moab.eventlog.EventLog;
import org.moab.eventsource.AccountCreateEvent;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class AccountCreateTest {

    private LocalDate dob = LocalDate.of(1970, 01, 01);

    @Test
    public void createEventCommandPersistsAndGetsAccountID() {
        EventLog eventLog = new EventLog();
        CreateAccountHandler createAccountHandler = new CreateAccountHandler(eventLog);

        AccountCreateEvent command = new AccountCreateEvent("Silver Surfer", "SilverSurferID", dob);
        createAccountHandler.handle(command);

        assertThat(eventLog.size()).isEqualTo(1);
    }
}
