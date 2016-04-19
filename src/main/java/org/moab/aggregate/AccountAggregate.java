package org.moab.aggregate;

import lombok.Getter;
import org.moab.eventlog.EventLog;
import org.moab.events.AccountCreated;
import org.moab.events.MOABEvent;

import java.time.LocalDate;

public class AccountAggregate {
    @Getter private long balance;
    @Getter private String clientName;
    @Getter private String clientID;
    @Getter private LocalDate clientDoB;
    @Getter private String accountNumber;

    private EventLog appliedEvents;

    public AccountAggregate() {
        appliedEvents = new EventLog();
    }

    /** apply an event to update this aggregate; the new state is applied incrementally
     *
     * @param accountCreated an AccountCreated, such as AccountCreated
     * @return if the event was applied or not.  `false` if it is a duplicate.
     */
    public boolean apply(AccountCreated accountCreated) {
        if (appliedEvents.add(accountCreated)) {
            balance = 0;
            clientName = accountCreated.getClientName();
            accountNumber = accountCreated.getAccountNumber();
            clientDoB = accountCreated.getClientDoB();
            clientID = accountCreated.getClientID();
            return true;
        }
        return false;
    }

    public boolean apply(MOABEvent moabEvent) {
        return false;
    }

}
