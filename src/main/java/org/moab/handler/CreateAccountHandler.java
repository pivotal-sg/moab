package org.moab.handler;

import org.moab.eventlog.EventLog;
import org.moab.eventsource.AccountCreateEvent;
import org.moab.eventsource.MOABEvent;

import java.util.UUID;

public class CreateAccountHandler implements MOABHanlder {
    private EventLog eventLog;

    public CreateAccountHandler(EventLog eventLog) {
        this.eventLog = eventLog;
    }

    @Override
    public void handle(MOABEvent command) {
        if (command.getName() == "createEvent") {
            AccountCreateEvent createCommand = (AccountCreateEvent) command;
            for (MOABEvent event: eventLog) {
                if (((AccountCreateEvent) event).getClientID() == createCommand.getClientID()) {
                    return;
                }
            }
        handleCreateAccount(createCommand);
        }
    }

    private void handleCreateAccount(AccountCreateEvent command) {
        UUID accountUUID = UUID.randomUUID();
        command.setAccountNumber(accountUUID.toString()); // assume uniqueness here; its a UUID.
        eventLog.add(command);
    }
}
