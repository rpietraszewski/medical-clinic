package com.rpietraszewski.medicalclinic.handler;

import com.rpietraszewski.medicalclinic.exception.PatientEmailAlreadyExistsException;
import com.rpietraszewski.medicalclinic.exception.PatientIdCardNoChangeException;
import com.rpietraszewski.medicalclinic.exception.PatientNotFoundException;
import com.rpietraszewski.medicalclinic.exception.PatientNullFieldsException;
import com.rpietraszewski.medicalclinic.model.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@RestControllerAdvice
public class MedicalClinicExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PatientNotFoundException.class)
    protected MessageDTO onPatientNotFoundErrorHandler(PatientNotFoundException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PatientEmailAlreadyExistsException.class)
    protected MessageDTO onPatientEmailExistsErrorHandler(PatientEmailAlreadyExistsException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PatientNullFieldsException.class)
    protected MessageDTO onPatientNullFieldsErrorHandler(PatientNullFieldsException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PatientIdCardNoChangeException.class)
    protected MessageDTO onPatientIdCardNoChangeErrorHandler(PatientIdCardNoChangeException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }
}
