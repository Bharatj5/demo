package com.sp.co.uk.Service;

import com.sp.co.uk.dao.AccountRepository;
import com.sp.co.uk.domain.Account;
import com.sp.co.uk.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Before
    public void setUp() {
        accountService = new AccountService(accountRepository);
    }

    @Test
    public void getAccount_returnsAccount() {
        when(accountRepository.findById(1000001l)).thenReturn(
                Optional.of(new Account(1000001l, "John Doe")));

        Account account = accountService.findById(1000001l);
        assertThat(account.getAccountNumber()).isEqualTo(1000001l);
        assertThat(account.getName()).isEqualTo("John Doe");
    }
}
