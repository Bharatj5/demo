package com.sp.co.uk.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNT")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long accountNumber;

    @Column(name = "name", nullable = false)
    private String name;
}
