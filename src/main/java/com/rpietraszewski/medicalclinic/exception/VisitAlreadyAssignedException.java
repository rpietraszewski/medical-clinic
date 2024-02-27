package com.rpietraszewski.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class VisitAlreadyAssignedException extends MedicalClinicException{
    public VisitAlreadyAssignedException(String message) {                                                                                                                                  
        super(message, HttpStatus.CONFLICT);
    }
}
