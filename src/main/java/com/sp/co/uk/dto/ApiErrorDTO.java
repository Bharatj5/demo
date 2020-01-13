package com.sp.co.uk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ApiErrorDTO {

    private HttpStatus status;
    private String message;
    private String errors;

}
