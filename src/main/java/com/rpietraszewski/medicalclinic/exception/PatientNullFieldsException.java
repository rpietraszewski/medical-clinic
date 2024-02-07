package com.rpietraszewski.medicalclinic.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class PatientNullFieldsException extends RuntimeException{
    private String message;
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
}
