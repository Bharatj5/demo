package com.sp.co.uk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SmartMeterDTO {

    private long id;

    private long accountNumber;

    @JsonProperty("electricityRead")
    private long electricitySmartRead;

    @JsonProperty("gasRead")
    private long gasSmartRead;
}
