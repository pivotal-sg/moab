package org.moab.aggregate;

import org.junit.Test;
import org.moab.eventsource.AccountCreateEvent;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class AccountTest {

    private LocalDate dob = LocalDate.of(1970, 01, 01);
    String clientName = "Silver Surfer";

    private AccountCreateEvent createAccountEvent(String clientName, String clientId, String accountNumber) {
        AccountCreateEvent createEvent = new AccountCreateEvent(clientName, clientId, dob);
        createEvent.setUUID(UUID.randomUUID());
        createEvent.setCreated(ZonedDateTime.now());
        createEvent.setAccountNumber(accountNumber);
        return createEvent;
    }

    @Test
    public void createAccountUpdatesState() {
        // With a new event, create an account

        AccountAggregate ag = new AccountAggregate();
        AccountCreateEvent createEvent = createAccountEvent(clientName, "SilverSurferID", "1");

        ag.apply(createEvent);

        assertThat(ag.getBalance()).isEqualTo(0);
        assertThat(ag.getClientName()).isEqualTo(clientName);
        assertThat(ag.getAccountNumber()).isEqualTo("1");
        assertThat(ag.getClientDoB()).isEqualTo(dob);
        assertThat(ag.getClientID()).isEqualTo("SilverSurferID");
    }

    @Test
    public void doesNotApplySameEventTwice() {
        AccountAggregate firstAg = new AccountAggregate();
        AccountAggregate secondAg = new AccountAggregate();
        AccountCreateEvent createEvent = createAccountEvent(clientName, "SilverSurferID", "1");

        assertThat(firstAg.apply(createEvent)).isTrue();
        assertThat(firstAg.apply(createEvent)).isFalse(); // isn't applied to the same ag
    }

    @Test
    public void canCreateTwoDifferntAccounts() {
        AccountAggregate firstAg = new AccountAggregate();
        AccountAggregate secondAg = new AccountAggregate();
        AccountCreateEvent firstCreateEvent = createAccountEvent(clientName, "SilverSurferID", "1");
        AccountCreateEvent secondCreateEvent = createAccountEvent("Galcticus", "BigGuyID", "2");

        assertThat(firstAg.apply(firstCreateEvent)).isTrue();
        assertThat(secondAg.apply(secondCreateEvent)).isTrue();
    }
}
