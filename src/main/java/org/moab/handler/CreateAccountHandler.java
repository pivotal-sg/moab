package org.moab.handler;

import org.moab.aggregate.AccountAggregate;
import org.moab.command.AccountCreateCommand;
import org.moab.command.MOABCommand;
import org.moab.eventlog.EventLog;
import org.moab.events.AccountCreated;

import java.util.UUID;

public class CreateAccountHandler implements MOABHandler {
    private EventLog eventLog;

    public CreateAccountHandler(EventLog eventLog) {
        this.eventLog = eventLog;
    }

    public void handle(AccountCreateCommand command) {
        handleCreateAccount(command);
    }

    private void handleCreateAccount(AccountCreateCommand command) {
        AccountCreated accountCreated = AccountCreated.fromCommand(command);
        AccountAggregate ag = new AccountAggregate();

        UUID accountUUID = UUID.randomUUID();
        accountCreated.setAccountNumber(accountUUID.toString()); // assume uniqueness here; its a UUID.
        if (eventLog.add(accountCreated)) {
            ag.apply(accountCreated);
        }
    }

}
