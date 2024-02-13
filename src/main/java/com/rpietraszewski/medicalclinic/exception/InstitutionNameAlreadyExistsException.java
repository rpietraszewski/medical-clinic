package com.rpietraszewski.medicalclinic.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InstitutionNameAlreadyExistsException extends MedicalClinicException {
    public InstitutionNameAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
