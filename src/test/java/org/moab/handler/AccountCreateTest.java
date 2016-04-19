package org.moab.handler;

import org.junit.Test;
import org.moab.command.AccountCreateCommand;
import org.moab.eventlog.EventLog;
import org.moab.events.AccountCreated;
import org.moab.exception.UnsupportedCommandException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class AccountCreateTest {

    private LocalDate dob = LocalDate.of(1970, 01, 01);

    @Test
    public void createEventCommandPersistsAndGetsAccountID() throws UnsupportedCommandException {
        EventLog eventLog = new EventLog();
        CreateAccountHandler createAccountHandler = new CreateAccountHandler(eventLog);

        AccountCreateCommand command = new AccountCreateCommand("Silver Surfer", "SilverSurferID", dob);
        createAccountHandler.handle(command);

        assertThat(eventLog.size()).isEqualTo(1);
    }

}
