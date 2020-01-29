package com.sp.co.uk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Relation(collectionRelation = "smartMeterReads")
public class SmartMeterReadDTO {

    private long id;

    @JsonProperty("account")
    private long accountNumber;

    @JsonProperty("electricityRead")
    private long electricitySmartRead;

    @JsonProperty("gasRead")
    private long gasSmartRead;
}
