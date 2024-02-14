package com.rpietraszewski.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class DoctorInstitutionAlreadyAssignedException extends MedicalClinicException {
    public DoctorInstitutionAlreadyAssignedException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
