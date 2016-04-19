package org.moab.events;

import lombok.Getter;
import lombok.Setter;
import org.moab.command.AccountCreateCommand;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public class AccountCreated implements MOABEvent {
    private UUID uuid;
    @Setter private ZonedDateTime created;
    @Getter private int version = 0;
    @Getter private String name = "createEvent";
    @Getter @Setter private String accountNumber;
    @Getter @Setter private String clientName;
    @Getter @Setter private String clientID;
    @Getter @Setter private LocalDate clientDoB;

    public static AccountCreated fromCommand(AccountCreateCommand command) {
        return new AccountCreated(command.getClientName(), command.getClientID(), command.getClientDoB());

    }

    public AccountCreated() {
        super();
    }

    public AccountCreated(String clientName, String clientID, LocalDate dob) {
        this.clientName = clientName;
        this.clientID = clientID;
        this.clientDoB = dob;
    }

    @Override
    public ZonedDateTime createdAt() {
        return created;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

}
