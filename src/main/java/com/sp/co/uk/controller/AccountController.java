package com.sp.co.uk.controller;

import com.sp.co.uk.domain.Account;
import com.sp.co.uk.exception.NotFoundException;
import com.sp.co.uk.service.AccountService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/api/accounts/{id}")
    public ResponseEntity<EntityModel<Account>> findOne(@PathVariable long id) {
        return accountService.findById(id)
                .map(account -> new EntityModel<>(account,
                        linkTo(methodOn(AccountController.class)
                                .findOne(account.getAccountNumber())).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Account number " + id + " not found"));
    }
}
