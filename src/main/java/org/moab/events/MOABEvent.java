package org.moab.events;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface MOABEvent {
    public UUID getUUID();
    public ZonedDateTime createdAt();
    public int getVersion();
    public String getName();
    public void setUUID(UUID uuid);

    public void setCreated(ZonedDateTime date);
}
