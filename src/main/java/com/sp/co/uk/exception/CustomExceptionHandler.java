package com.sp.co.uk.exception;

import com.sp.co.uk.dto.ApiErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public static ResponseEntity<Object> handleAll() {
        final ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.BAD_REQUEST, "Bad request", "Bad request");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    @ExceptionHandler({NotFoundException.class})
    public static ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        final ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.NOT_FOUND, ex.getMessage(), "Record not found");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}
