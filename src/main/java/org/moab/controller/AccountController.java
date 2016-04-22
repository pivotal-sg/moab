package org.moab.controller;

import org.moab.aggregate.AccountAggregate;
import org.moab.bus.MessageBus;
import org.moab.command.AccountCreateCommand;
import org.moab.command.AccountShowCommand;
import org.moab.dto.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    private MessageBus messageBus;

    @Autowired
    public AccountController(MessageBus messageBus) {
        this.messageBus = messageBus;
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@RequestBody AccountCreateCommand command) {
        AccountAggregate ag = messageBus.send(command);
        return new Account(ag.getAccountNumber(),
                ag.getClientName(),
                ag.getClientID(),
                ag.getClientDoB(),
                ag.getBalance());
    }

    @RequestMapping(value = "/account/{accountNumber}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Account> show(@PathVariable String accountNumber) {
        AccountShowCommand command = new AccountShowCommand(accountNumber);
        AccountAggregate ag = messageBus.send(command);
        if (ag == null) { return makeResponse(null, HttpStatus.NOT_FOUND); }
        return makeResponse(new Account(ag.getAccountNumber(),
                                        ag.getClientName(),
                                        ag.getClientID(),
                                        ag.getClientDoB(),
                                        ag.getBalance()), HttpStatus.OK);

    }

    private ResponseEntity<Account> makeResponse(Account account, HttpStatus status) {
        return new ResponseEntity<Account>(account, status);
    }
}
