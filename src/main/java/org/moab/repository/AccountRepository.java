package org.moab.repository;

import org.moab.aggregate.AccountAggregate;
import org.moab.events.AccountCreated;
import org.moab.events.MOABEvent;
import org.springframework.stereotype.Component;

@Component
public class AccountRepository {
    // Map accountNumber to an event log.
    private EventLog eventLog;

    public AccountRepository() {
        eventLog = new EventLog();
    }

    public boolean add(MOABEvent event) {
        return eventLog.add(event);
    }

    public AccountAggregate find(String accountNumber) {
        for (MOABEvent event : eventLog.getAllForEventType(AccountCreated.class)) {
            AccountCreated ac = (AccountCreated) event;
            if (ac.getAccountNumber().equals(accountNumber)) {
                AccountAggregate ag = new AccountAggregate();
                ag.apply(ac);
                return ag;
            }
        }
        return null;
    }

    public Integer size() {
        return eventLog.size();
    }
}
