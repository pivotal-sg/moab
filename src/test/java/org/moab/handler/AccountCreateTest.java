package org.moab.handler;

import org.junit.Before;
import org.junit.Test;
import org.moab.aggregate.AccountAggregate;
import org.moab.command.AccountCreateCommand;
import org.moab.repository.AccountRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountCreateTest {

    private LocalDate dob = LocalDate.of(1970, 01, 01);
    private AccountRepository accountRepo;
    private CreateAccountHandler createAccountHandler;

    @Before
    public void setUp() {
        accountRepo = new AccountRepository();
        createAccountHandler = new CreateAccountHandler(accountRepo);
    }

    @Test
    public void createEventCommandPersists() {
        AccountCreateCommand command = new AccountCreateCommand("Silver Surfer", "SilverSurferID", dob);
        AccountAggregate ag = createAccountHandler.handle(command);

        AccountAggregate retrieved = accountRepo.find(ag.getAccountNumber());
        assertThat(retrieved.getAccountNumber()).isEqualTo(ag.getAccountNumber());
    }

    @Test
    public void createAccountReturnsAccountAggregate() {
        AccountCreateCommand command = new AccountCreateCommand("Silver Surfer", "SilverSurferID", dob);
        AccountAggregate ag = createAccountHandler.handle(command);

        assertThat(ag.getAccountNumber()).isNotEmpty();
    }

}
