package com.rpietraszewski.medicalclinic.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PatientIdCardNoChangeException extends MedicalClinicException {
    public PatientIdCardNoChangeException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
