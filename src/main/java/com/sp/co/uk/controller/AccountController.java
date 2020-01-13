package com.sp.co.uk.controller;

import com.sp.co.uk.dao.AccountRepository;
import com.sp.co.uk.domain.Account;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AccountController {

    private AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/api/account/{id}")
    public ResponseEntity<EntityModel<Account>> findOne(@PathVariable long id) {
        return accountRepository.findById(id)
            .map(account -> new EntityModel<>(account,
                linkTo(methodOn(AccountController.class).findOne(account.getAccountNumber())).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
