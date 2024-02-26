package com.rpietraszewski.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class VisitWrongDateTimeException extends MedicalClinicException{
    public VisitWrongDateTimeException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
