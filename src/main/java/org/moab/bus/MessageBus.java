package org.moab.bus;

import org.moab.command.AccountCreateCommand;
import org.moab.command.MOABCommand;
import org.moab.exception.UnsupportedCommandException;
import org.moab.handler.CreateAccountHandler;
import org.moab.handler.MOABHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageBus {
    CreateAccountHandler createAccountHandler;

    public void send(AccountCreateCommand command) {

        createAccountHandler.handle(command);

    }
}
