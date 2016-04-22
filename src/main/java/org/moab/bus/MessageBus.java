package org.moab.bus;

import org.moab.aggregate.AccountAggregate;
import org.moab.command.AccountCreateCommand;
import org.moab.command.AccountShowCommand;
import org.moab.handler.ShowAccountHandler;
import org.moab.repository.AccountRepository;
import org.moab.handler.CreateAccountHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageBus {
    CreateAccountHandler createAccountHandler;
    ShowAccountHandler showAccountHandler;

    private AccountRepository accountRepository;

    @Autowired
    public MessageBus(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        createAccountHandler = new CreateAccountHandler(accountRepository);
        showAccountHandler = new ShowAccountHandler(accountRepository);
    }

    public AccountAggregate send(AccountCreateCommand command) {
        return createAccountHandler.handle(command);
    }

    public AccountAggregate send(AccountShowCommand command) {
        return showAccountHandler.handle(command);
    }
}
