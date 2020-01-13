package com.sp.co.uk.dao;

import com.sp.co.uk.domain.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void getAccount_returnsAccountDetails() {
        Account account = entityManager.merge(
                new Account(1000001l, "John Doe"));

        Optional<Account> testAccount = accountRepository.findById(account.getAccountNumber());

        assertThat(testAccount.get().getAccountNumber()).isEqualTo(account.getAccountNumber());
        assertThat(testAccount.get().getName()).isEqualTo(account.getName());
    }
}
