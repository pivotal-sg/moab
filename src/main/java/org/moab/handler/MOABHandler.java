package org.moab.handler;

import org.moab.command.MOABCommand;
import org.moab.exception.UnsupportedCommandException;

/**
 * Created by neo on 19/4/16.
 */
public interface MOABHandler {
    public void handle(Object o) throws UnsupportedCommandException;
}
