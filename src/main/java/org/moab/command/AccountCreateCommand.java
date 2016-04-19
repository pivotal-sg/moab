package org.moab.command;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class AccountCreateCommand implements MOABCommand {
    @Getter private int version = 0;
    @Getter private String name = "createEvent";
    @Getter @Setter private String clientName;
    @Getter @Setter private String clientID;
    @Getter @Setter private LocalDate clientDoB;

    public AccountCreateCommand(String clientName, String clientID, LocalDate dob) {
        this.clientName = clientName;
        this.clientID = clientID;
        this.clientDoB = dob;
    }
}
