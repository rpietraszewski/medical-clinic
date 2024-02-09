package com.rpietraszewski.medicalclinic.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PatientNotFoundException extends MedicalClinicException {
    public PatientNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
