package com.sp.co.uk.service;

import com.sp.co.uk.dao.AccountRepository;
import com.sp.co.uk.domain.Account;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> findById(Long accountNumber) {
        return accountRepository.findById(accountNumber);
    }
}
