package com.rpietraszewski.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class VisitPastDateException extends MedicalClinicException {
    public VisitPastDateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
