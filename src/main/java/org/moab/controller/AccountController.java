package org.moab.controller;

import org.moab.aggregate.AccountAggregate;
import org.moab.bus.MessageBus;
import org.moab.command.AccountCreateCommand;
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
        AccountAggregate ag = this.messageBus.send(command);
        return new Account(ag.getAccountNumber(),
                ag.getClientName(),
                ag.getClientID(),
                ag.getClientDoB());
    }

}
