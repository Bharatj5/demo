package com.sp.co.uk.Service;

import com.sp.co.uk.dao.SmartMeterReadRepository;
import com.sp.co.uk.domain.Account;
import com.sp.co.uk.domain.SmartMeterRead;
import com.sp.co.uk.service.SmartMeterReadService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SmartMeterReadServiceTest {

    private SmartMeterReadService smartMeterReadService;

    @Mock
    private SmartMeterReadRepository smartMeterReadRepository;

    private SmartMeterRead smartMeterRead;

    @Before
    public void setUp() {
        this.smartMeterReadService = new SmartMeterReadService(smartMeterReadRepository);

        Account testAccount = new Account();
        testAccount.setAccountNumber(1000001l);

        smartMeterRead = new SmartMeterRead();
        smartMeterRead.setAccount(testAccount);
        smartMeterRead.setElectricitySmartRead(800l);
        smartMeterRead.setGasSmartRead(500l);
        smartMeterRead.setGasMeterId("G123");
        smartMeterRead.setElectricityMeterId("E123");
    }

    @Test
    public void getSmartMeterReads_shouldReturnReads() {

        given(smartMeterReadRepository.findByAccount_accountNumber(1000001l)).willReturn(
                Arrays.asList(smartMeterRead));

        List<SmartMeterRead> reads = smartMeterReadService.getSmartMeterReads(1000001);
        SmartMeterRead read = reads.get(0);

        assertThat(read.getAccount().getAccountNumber()).isEqualTo(1000001);
        assertThat(read.getGasMeterId()).isEqualTo("G123");
        assertThat(read.getElectricityMeterId()).isEqualTo("E123");
        assertThat(read.getElectricitySmartRead()).isEqualTo(800);
        assertThat(read.getGasSmartRead()).isEqualTo(500);
    }
}
