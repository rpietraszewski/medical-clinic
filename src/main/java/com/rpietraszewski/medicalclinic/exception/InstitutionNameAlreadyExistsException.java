package com.rpietraszewski.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class InstitutionNameAlreadyExistsException extends MedicalClinicException {
    public InstitutionNameAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
