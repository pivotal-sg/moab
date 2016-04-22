package org.moab.handler;

import org.moab.aggregate.AccountAggregate;
import org.moab.command.AccountShowCommand;
import org.moab.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ShowAccountHandler {
    private AccountRepository accountRepository;

    @Autowired
    public ShowAccountHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountAggregate handle(AccountShowCommand command) {
        return accountRepository.find(command.getAccountNumber());
    }
}
