package com.rpietraszewski.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class PatientPasswordSameValueException extends MedicalClinicException {
    public PatientPasswordSameValueException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
