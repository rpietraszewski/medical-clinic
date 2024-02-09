package com.rpietraszewski.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class PatientIdCardNoChangeException extends MedicalClinicException {
    public PatientIdCardNoChangeException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
