package com.rpietraszewski.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class VisitNullFieldsException extends MedicalClinicException{
    public VisitNullFieldsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
