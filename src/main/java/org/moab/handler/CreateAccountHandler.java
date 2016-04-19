package org.moab.handler;

import org.moab.aggregate.AccountAggregate;
import org.moab.command.AccountCreateCommand;
import org.moab.command.MOABCommand;
import org.moab.eventlog.EventLog;
import org.moab.events.AccountCreated;
import org.moab.exception.UnsupportedCommandException;

import java.util.UUID;

public class CreateAccountHandler implements MOABHandler {
    private EventLog eventLog;

    public CreateAccountHandler(EventLog eventLog) {
        this.eventLog = eventLog;
    }

    public void handle(Object o) throws UnsupportedCommandException {
        try {
            AccountCreateCommand cmd = (AccountCreateCommand) o;
            handleCreateAccount(cmd);
        } catch(ClassCastException e) {
            throw new UnsupportedCommandException();
        }
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
