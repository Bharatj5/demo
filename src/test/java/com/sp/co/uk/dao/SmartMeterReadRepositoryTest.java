package com.sp.co.uk.dao;

import com.sp.co.uk.domain.Account;
import com.sp.co.uk.domain.SmartMeterRead;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SmartMeterReadRepositoryTest {

    @Autowired
    private SmartMeterReadRepository smartMeterReadRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void getSmartMeterReads_returnsSmartMeterReads() {
        Account account = entityManager.merge(
                new Account(1000001l, "John Doe"));

        SmartMeterRead meterRead = entityManager.merge(
                new SmartMeterRead(1l, account,
                800l, 500l,
                        "G123", "E123"));

        List<SmartMeterRead> reads = smartMeterReadRepository.findByAccount_accountNumber(account.getAccountNumber());
        SmartMeterRead read = reads.get(0);

        assertThat(read.getAccount().getAccountNumber()).isEqualTo(meterRead.getAccount().getAccountNumber());
        assertThat(read.getGasMeterId()).isEqualTo(meterRead.getGasMeterId());
        assertThat(read.getElectricityMeterId()).isEqualTo(meterRead.getElectricityMeterId());
        assertThat(read.getElectricitySmartRead()).isEqualTo(meterRead.getElectricitySmartRead());
        assertThat(read.getGasSmartRead()).isEqualTo(meterRead.getGasSmartRead());
    }
}
