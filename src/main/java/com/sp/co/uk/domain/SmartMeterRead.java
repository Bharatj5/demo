package com.sp.co.uk.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "SMART_METER")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SmartMeterRead {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_number", nullable = false)
    private Account account;

    @Column(name = "GAS_SMART_READ")
    private Long gasSmartRead;

    @Column(name = "ELEC_SMART_READ")
    private Long electricitySmartRead;

    @Column(name = "GAS_ID")
    private String gasMeterId;

    @Column(name = "ELEC_ID")
    private String electricityMeterId;
}
