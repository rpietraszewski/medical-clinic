package com.rpietraszewski.medicalclinic.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DoctorInstitutionAlreadyAssignedException extends MedicalClinicException {
    public DoctorInstitutionAlreadyAssignedException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
