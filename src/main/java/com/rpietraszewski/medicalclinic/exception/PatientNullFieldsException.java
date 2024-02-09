package com.rpietraszewski.medicalclinic.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PatientNullFieldsException extends MedicalClinicException {
    public PatientNullFieldsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
