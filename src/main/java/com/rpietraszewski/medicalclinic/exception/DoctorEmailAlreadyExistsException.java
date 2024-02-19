package com.rpietraszewski.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class DoctorEmailAlreadyExistsException extends MedicalClinicException {
    public DoctorEmailAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
