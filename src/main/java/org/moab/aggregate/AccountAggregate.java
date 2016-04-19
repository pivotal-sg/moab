package org.moab.aggregate;

import lombok.Getter;
import org.moab.eventlog.EventLog;
import org.moab.eventsource.AccountCreateEvent;
import org.moab.eventsource.MOABEvent;

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
     * @param moabEvent a MOABEvent, such as AccountCreateEvent
     * @return if the event was applied or not.  `false` if it is a duplicate.
     */
    public boolean apply(MOABEvent moabEvent) {
        switch (moabEvent.getName()) {
            case "createEvent":
                AccountCreateEvent createEvent = (AccountCreateEvent) moabEvent;
                // only apply the event if we haven't added in this event already
                if (appliedEvents.add(createEvent)) {
                    balance = 0;
                    clientName = createEvent.getClientName();
                    accountNumber = createEvent.getAccountNumber();
                    clientDoB = createEvent.getClientDoB();
                    clientID = createEvent.getClientID();
                    return true;
                }
                return false;
            default:
                return false;
        }
    }
}
