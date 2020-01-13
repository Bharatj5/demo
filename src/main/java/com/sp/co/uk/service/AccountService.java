package com.sp.co.uk.service;

import com.sp.co.uk.dao.AccountRepository;
import com.sp.co.uk.domain.Account;
import com.sp.co.uk.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findById(long accountNumber) {
        return accountRepository
                .findById(accountNumber)
                .orElseThrow(
                        () -> new NotFoundException("Account " + accountNumber + " not found")
                );
    }
}
