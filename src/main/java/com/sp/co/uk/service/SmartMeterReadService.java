package com.sp.co.uk.service;

import com.sp.co.uk.dao.SmartMeterReadRepository;
import com.sp.co.uk.domain.SmartMeterRead;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class SmartMeterReadService {

    @Autowired
    private SmartMeterReadRepository smartMeterReadRepository;


    public SmartMeterReadService(SmartMeterReadRepository smartMeterReadRepository) {
        this.smartMeterReadRepository = smartMeterReadRepository;
    }

    public List<SmartMeterRead> getSmartMeterReads(long accountNumber) {
        return smartMeterReadRepository.findByAccount_accountNumber(accountNumber);
    }

    public Optional<SmartMeterRead> findById(long id) {
        return smartMeterReadRepository.findById(id);
    }
}
