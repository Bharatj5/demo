package com.sp.co.uk.dao;

import com.sp.co.uk.domain.SmartMeterRead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmartMeterReadRepository extends JpaRepository<SmartMeterRead, Long> {

    List<SmartMeterRead> findByAccount_accountNumber(long accountNumber);
}
