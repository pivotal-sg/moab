package org.moab.handler;

import org.junit.Test;
import org.moab.command.AccountCreateCommand;
import org.moab.eventlog.EventLog;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountCreateTest {

    private LocalDate dob = LocalDate.of(1970, 01, 01);

    @Test
    public void createEventCommandPersistsAndGetsAccountID() {
        EventLog eventLog = new EventLog();
        CreateAccountHandler createAccountHandler = new CreateAccountHandler(eventLog);

        AccountCreateCommand command = new AccountCreateCommand("Silver Surfer", "SilverSurferID", dob);
        assertThat(eventLog.size()).isEqualTo(1);
    }

}
