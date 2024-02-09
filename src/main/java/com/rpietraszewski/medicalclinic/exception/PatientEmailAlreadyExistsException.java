package com.rpietraszewski.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class PatientEmailAlreadyExistsException extends MedicalClinicException {
    public PatientEmailAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
