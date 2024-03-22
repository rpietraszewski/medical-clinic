package com.rpietraszewski.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class DateNullException extends MedicalClinicException {
    public DateNullException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
