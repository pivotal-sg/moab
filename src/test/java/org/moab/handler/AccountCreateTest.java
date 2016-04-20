package org.moab.handler;

import org.junit.Before;
import org.junit.Test;
import org.moab.aggregate.AccountAggregate;
import org.moab.command.AccountCreateCommand;
import org.moab.eventlog.EventLog;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountCreateTest {

    private LocalDate dob = LocalDate.of(1970, 01, 01);
    private EventLog eventLog;
    private CreateAccountHandler createAccountHandler;

    @Before
    public void setUp() {
        eventLog = new EventLog();
        createAccountHandler = new CreateAccountHandler(eventLog);
    }

    @Test
    public void createEventCommandPersists() {
        AccountCreateCommand command = new AccountCreateCommand("Silver Surfer", "SilverSurferID", dob);
        createAccountHandler.handle(command);

        assertThat(eventLog.size()).isEqualTo(1);
    }

    @Test
    public void createAccountReturnsAccountAggregate() {
        AccountCreateCommand command = new AccountCreateCommand("Silver Surfer", "SilverSurferID", dob);
        AccountAggregate ag = createAccountHandler.handle(command);

        assertThat(ag.getAccountNumber()).isNotEmpty();
    }

}
