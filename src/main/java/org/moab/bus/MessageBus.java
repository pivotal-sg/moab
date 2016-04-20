package org.moab.bus;

import org.moab.aggregate.AccountAggregate;
import org.moab.command.AccountCreateCommand;
import org.moab.eventlog.EventLog;
import org.moab.handler.CreateAccountHandler;

public class MessageBus {
    CreateAccountHandler createAccountHandler;

    private EventLog eventLog;

    public MessageBus(EventLog eventLog) {
        this.eventLog = eventLog;
        createAccountHandler = new CreateAccountHandler(eventLog);
    }

    public AccountAggregate send(AccountCreateCommand command) {
        return createAccountHandler.handle(command);
    }
}
