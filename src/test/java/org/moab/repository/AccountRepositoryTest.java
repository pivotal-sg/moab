package org.moab.repository;

import org.junit.Test;
import org.moab.dto.Account;
import org.moab.events.AccountCreated;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class AccountRepositoryTest {

    @Test
    public void addEvent() {
        AccountRepository ar = new AccountRepository();

        ar.add(new AccountCreated("Name", "ID", LocalDate.now()));
        assertThat(ar.size()).isEqualTo(1);
    }

    @Test
    public void findEvent() {
        AccountRepository ar = new AccountRepository();
        AccountCreated ac = new AccountCreated("Name", "ID", LocalDate.now());
        ac.setAccountNumber("123");

        ar.add(ac);
        assertThat(ar.find("123")).isNotNull();
        assertThat(ar.find("123").getClientName()).isEqualTo("Name");
    }
}
