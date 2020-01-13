package com.sp.co.uk.dao;

import com.sp.co.uk.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
