package com.rpietraszewski.medicalclinic.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class PatientEmailAlreadyExistsException extends RuntimeException {
    private String message;
    private final HttpStatus httpStatus = HttpStatus.CONFLICT;
}
