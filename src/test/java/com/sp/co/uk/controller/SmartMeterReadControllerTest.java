package com.sp.co.uk.controller;

import com.sp.co.uk.domain.Account;
import com.sp.co.uk.domain.SmartMeterRead;
import com.sp.co.uk.exception.NotFoundException;
import com.sp.co.uk.service.AccountService;
import com.sp.co.uk.service.SmartMeterReadService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SmartMeterController.class)
public class SmartMeterReadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SmartMeterReadService smartMeterReadService;

    @MockBean
    private AccountService accountService;

    private SmartMeterRead smartMeterRead;
    private Account testAccount;


    @Before
    public void setUp() {
        testAccount = new Account(100001L, "TOM JOE");
        smartMeterRead = new SmartMeterRead();
        smartMeterRead.setId(1L);
        smartMeterRead.setAccount(testAccount);
        smartMeterRead.setElectricitySmartRead(800L);
        smartMeterRead.setGasSmartRead(500L);
        smartMeterRead.setGasMeterId("T101");
        smartMeterRead.setElectricityMeterId("E101");
    }

    @Test
    public void getSmartMeterReads_shouldReturnSmartMeterReads() throws Exception {

        given(accountService.findById(testAccount.getAccountNumber()))
                .willReturn(Optional.of(testAccount));

        given(smartMeterReadService.getSmartMeterReads(100001L))
                .willReturn(Arrays.asList(smartMeterRead));

        mockMvc.perform(get("/api/smart/reads/100001"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(
                        jsonPath("$._embedded.smartMeterReads[0].electricityRead")
                                .value(800))
                .andExpect(
                        jsonPath("$._embedded.smartMeterReads[0].gasRead")
                                .value(500));
    }

    @Test
    public void getSmartMeterRead_shouldReturnSmartMeterReadDetails() throws Exception {

        given(smartMeterReadService.findById(1))
                .willReturn(Optional.of(smartMeterRead));

        mockMvc.perform(get("/api/smart/read/1"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("gasMeterId").value("T101"));
    }

    @Test
    public void whenMethodArgumentMismatch_thenBadRequest() throws Exception {
        given(smartMeterReadService.getSmartMeterReads(anyLong()))
                .willThrow(new NotFoundException(""));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/smart/reads/404"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenMethodReadMismatch_thenNotFound() throws Exception {
        given(smartMeterReadService.getSmartMeterReads(anyLong()))
                .willThrow(new NotFoundException(""));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/smart/read/404"))
                .andExpect(jsonPath("status").value("NOT_FOUND"))
                .andExpect(jsonPath("message ").value("reads for number 404 not found"))
                .andExpect(jsonPath("errors").value("Record not found"));
    }

}
