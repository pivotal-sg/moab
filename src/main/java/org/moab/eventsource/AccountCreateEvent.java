package org.moab.eventsource;

import lombok.Getter;
import lombok.Setter;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public class AccountCreateEvent implements MOABEvent {
    @Getter @Setter private UUID uuid;
    @Setter private ZonedDateTime created;
    @Getter private int version = 0;
    @Getter private String name = "createEvent";
    @Getter @Setter private String accountNumber;
    @Getter @Setter private String clientName;
    @Getter @Setter private String clientID;
    @Getter @Setter private LocalDate clientDoB;

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public ZonedDateTime createdAt() {
        return created;
    }

    @Override
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public AccountCreateEvent() {
        super();
    }

    public AccountCreateEvent(String clientName, String clientID, LocalDate dob) {
        this.clientName = clientName;
        this.clientID = clientID;
        this.clientDoB = dob;
    }
}
