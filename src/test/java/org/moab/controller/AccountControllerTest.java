package org.moab.controller;


import org.junit.Before;
import org.junit.Test;
import org.moab.bus.MessageBus;
import org.moab.command.AccountCreateCommand;
import org.moab.dto.Account;
import org.moab.repository.AccountRepository;
import org.moab.handler.CreateAccountHandler;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

public class AccountControllerTest {


    private AccountController accountController;
    private LocalDate dob = LocalDate.of(1970, 01, 01);
    private MessageBus messageBus;
    private CreateAccountHandler createAccountHandler;

    @Before
    public void setUp(){
        AccountRepository eventLog = new AccountRepository();
        messageBus = new MessageBus(eventLog);
        accountController = new AccountController(messageBus);
        createAccountHandler = new CreateAccountHandler(eventLog);
    }

    @Test
    public void createAccountTest() {
        AccountCreateCommand command = new AccountCreateCommand("Silver Surfer", "SilverSurferID", dob);
        Account account = accountController.create(command);
        assertThat(account.getAccountNumber()).isNotEmpty();
    }

}
