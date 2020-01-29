package com.sp.co.uk.controller;

import com.sp.co.uk.domain.Account;
import com.sp.co.uk.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void account_shouldReturnAccount() throws Exception {
        given(accountService.findById(100001L))
                .willReturn(Optional.of(
                        new Account(100001L, "TOM JOHN")));

        mockMvc.perform(get("/api/accounts/100001").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("accountNumber").value(100001L));
    }

    @Test
    public void getAccount_shouldReturnNotFoundStatus() throws Exception {
        given(accountService.findById(404L))
                .willReturn(Optional.ofNullable(null));
        mockMvc.perform(get("/api/accounts/404").accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value("NOT_FOUND"))
                .andExpect(jsonPath("message ").value("Account number 404 not found"))
                .andExpect(jsonPath("errors").value("Record not found"));
    }

}
