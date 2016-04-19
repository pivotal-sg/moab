package org.moab.events;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Created by neo on 19/4/16.
 */
public interface MOABEvent {
    public UUID getUUID();
    public ZonedDateTime createdAt();
    public int getVersion();
    public String getName();
    public void setUUID(UUID uuid);

    public void setCreated(ZonedDateTime date);
}
