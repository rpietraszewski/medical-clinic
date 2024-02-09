package com.rpietraszewski.medicalclinic.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class MedicalClinicException extends RuntimeException {
    private final HttpStatus httpStatus;

    public MedicalClinicException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }
}
