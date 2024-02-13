package com.rpietraszewski.medicalclinic.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DoctorEmailAlreadyExistsException extends MedicalClinicException {
    public DoctorEmailAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
