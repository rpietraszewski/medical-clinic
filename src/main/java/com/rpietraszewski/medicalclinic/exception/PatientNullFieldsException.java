package com.rpietraszewski.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class PatientNullFieldsException extends MedicalClinicException {
    public PatientNullFieldsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
