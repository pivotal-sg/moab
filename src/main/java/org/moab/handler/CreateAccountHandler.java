package org.moab.handler;

import org.moab.aggregate.AccountAggregate;
import org.moab.command.AccountCreateCommand;
import org.moab.repository.AccountRepository;
import org.moab.events.AccountCreated;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class CreateAccountHandler {

    private AccountRepository accountRepository;

    @Autowired
    public CreateAccountHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountAggregate handle(AccountCreateCommand command) {
        AccountCreated accountCreated = AccountCreated.fromCommand(command);
        AccountAggregate ag = new AccountAggregate();

        UUID accountUUID = UUID.randomUUID();
        accountCreated.setAccountNumber(accountUUID.toString()); // assume uniqueness here; its a UUID.

        if (accountRepository.add(accountCreated)) {
            ag.apply(accountCreated);
        }
        return ag;
    }

}
