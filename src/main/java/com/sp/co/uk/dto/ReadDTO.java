package com.sp.co.uk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadDTO {
    private Long id;
    private Long gasRead;
    private Long electricityRead;
    private Long accountNumber;
    private String gasMeterId;
    private String electricityMeterId;
}
