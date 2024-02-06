package com.rpietraszewski.medicalclinic.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class PatientNotFoundException extends RuntimeException {
    private String message;
    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
}
