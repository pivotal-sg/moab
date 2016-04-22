package org.moab.controller;


import org.junit.Before;
import org.junit.Test;
import org.moab.bus.MessageBus;
import org.moab.command.AccountCreateCommand;
import org.moab.command.AccountShowCommand;
import org.moab.dto.Account;
import org.moab.events.AccountCreated;
import org.moab.repository.AccountRepository;
import org.moab.handler.CreateAccountHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

public class AccountControllerTest {


    private AccountController accountController;
    private LocalDate dob = LocalDate.of(1970, 01, 01);
    private MessageBus messageBus;
    private AccountRepository accountRepo;

    @Before
    public void setUp(){
        accountRepo = new AccountRepository();
        messageBus = new MessageBus(accountRepo);
        accountController = new AccountController(messageBus);
    }

    @Test
    public void createAccountTest() {
        AccountCreateCommand command = new AccountCreateCommand("Silver Surfer", "SilverSurferID", dob);
        Account account = accountController.create(command);
        assertThat(account.getAccountNumber()).isNotEmpty();
    }

    @Test
    public void showAccountTest() {
        String accountNumber = "123";
        String clientID = "N39482";
        String clientName = "Jason";
        AccountCreated accountEvent = new AccountCreated(clientName, clientID, LocalDate.now());
        accountEvent.setAccountNumber(accountNumber);
        accountRepo.add(accountEvent);

        ResponseEntity<Account> account = accountController.show(accountNumber);
        assertThat(account.getBody().getAccountNumber()).isEqualTo(accountNumber);
        assertThat(account.getBody().getClientName()).isEqualTo(clientName);
        assertThat(account.getBody().getBalance()).isEqualTo(0);
    }

    @Test
    public void showAccountTestWithNonExistentAccountNumber() {
        String accountNumber = "123";
        String clientID = "N39482";
        String clientName = "Jason";
        AccountCreated accountEvent = new AccountCreated(clientName, clientID, LocalDate.now());
        accountEvent.setAccountNumber(accountNumber);
        accountRepo.add(accountEvent);

        String wrongAccountNumber = "I am A Teapot!";
        ResponseEntity<Account> account = accountController.show(wrongAccountNumber);
        assertThat(account.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
