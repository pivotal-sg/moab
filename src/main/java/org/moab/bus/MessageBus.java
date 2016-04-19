package org.moab.bus;

import org.moab.command.MOABCommand;
import org.moab.exception.UnsupportedCommandException;
import org.moab.handler.MOABHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageBus {
    private HashMap<String, ArrayList<MOABHandler>> handlerRegistry;
    public MessageBus() {
        handlerRegistry = new HashMap<String, ArrayList<MOABHandler>>();
    }

    /** register a new handler for a given commandname.
     *
     * @param commandName
     * @param handler
     */
    public void register(String commandName, MOABHandler handler) {
        handlerRegistry.putIfAbsent(commandName, new ArrayList<MOABHandler>());
        handlerRegistry.get(commandName).add(handler);
    }

    public void send(String commandName, MOABCommand command) throws UnsupportedCommandException {
        ArrayList<MOABHandler> handlers = handlerRegistry.get(commandName);
        for(MOABHandler handler : handlers) {
           handler.handle(command);
        }

    }
}
