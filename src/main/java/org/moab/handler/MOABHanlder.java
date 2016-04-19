package org.moab.handler;

import org.moab.eventsource.MOABEvent;

/**
 * Created by neo on 19/4/16.
 */
public interface MOABHanlder {
    public void handle(MOABEvent command);
}
