package org.moab.bus;

import org.moab.command.AccountCreateCommand;
import org.moab.handler.CreateAccountHandler;

public class MessageBus {
    CreateAccountHandler createAccountHandler;

    public void send(AccountCreateCommand command) {
        createAccountHandler.handle(command);

    }
}
