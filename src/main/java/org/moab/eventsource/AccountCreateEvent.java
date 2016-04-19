package org.moab.eventsource;

import java.time.ZonedDateTime;
import java.util.UUID;

public class AccountCreateEvent implements MOABEvent {
    private UUID uuid;
    private ZonedDateTime created;
    private int version = 0;

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public ZonedDateTime createdAt() {
        return created;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }
}
