package com.rpietraszewski.medicalclinic.handler;

import com.rpietraszewski.medicalclinic.exception.*;
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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PatientPasswordSameValueException.class)
    protected MessageDTO onPatientPasswordSameValueErrorHandler(PatientPasswordSameValueException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DoctorEmailAlreadyExistsException.class)
    protected MessageDTO onDoctorEmailAlreadyExistsErrorHandler(DoctorEmailAlreadyExistsException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DoctorNotFoundException.class)
    protected MessageDTO onDoctorNotFoundErrorHandler(DoctorNotFoundException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DoctorNullFieldsException.class)
    protected MessageDTO onDoctorNullFieldsErrorHandler(DoctorNullFieldsException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DoctorInstitutionAlreadyAssignedException.class)
    protected MessageDTO onDoctorInstitutionAlreadyAssignedErrorHandler(DoctorInstitutionAlreadyAssignedException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InstitutionNameAlreadyExistsException.class)
    protected MessageDTO onInstitutionNameAlreadyExistsErrorHandler(InstitutionNameAlreadyExistsException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InstitutionNotFoundException.class)
    protected MessageDTO onInstitutionNotFoundErrorHandler(InstitutionNotFoundException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(VisitNotFoundException.class)
    protected MessageDTO onVisitNotFoundErrorHandler(VisitNotFoundException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(VisitAlreadyExistsException.class)
    protected MessageDTO onVisitAlreadyExitsErrorHandler(VisitAlreadyExistsException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(VisitNullFieldsException.class)
    protected MessageDTO onVisitNullFieldErrorHandler(VisitNullFieldsException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(VisitWrongDateTimeException.class)
    protected MessageDTO onVisitWrongDateTimeErrorHandler(VisitWrongDateTimeException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(VisitAlreadyAssignedException.class)
    protected MessageDTO onVisitAlreadyAssignedErrorHandler(VisitAlreadyAssignedException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(VisitPastDateException.class)
    protected MessageDTO onVisitPastDateErrorHandler(VisitPastDateException ex) {
        return new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus());
    }
}
