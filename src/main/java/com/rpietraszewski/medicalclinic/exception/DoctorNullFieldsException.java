package com.rpietraszewski.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class DoctorNullFieldsException extends MedicalClinicException {
    public DoctorNullFieldsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
