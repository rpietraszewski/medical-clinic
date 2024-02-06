package com.rpietraszewski.medicalclinic.handler;

import com.rpietraszewski.medicalclinic.exception.PatientEmailAlreadyExistsException;
import com.rpietraszewski.medicalclinic.exception.PatientIdCardNoChangeException;
import com.rpietraszewski.medicalclinic.exception.PatientNotFoundException;
import com.rpietraszewski.medicalclinic.exception.PatientNullFieldsException;
import com.rpietraszewski.medicalclinic.model.dto.MessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class ResponseEntityExceptionHandler extends org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler {
    @ExceptionHandler(PatientNotFoundException.class)
    protected ResponseEntity<MessageDTO> onPatientNotFoundErrorHandler(PatientNotFoundException ex){
        return ResponseEntity.status(ex.getHttpStatus()).body(new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus()));
    }

    @ExceptionHandler(PatientEmailAlreadyExistsException.class)
    protected ResponseEntity<MessageDTO> onPatientEmailExistsErrorHandler(PatientEmailAlreadyExistsException ex){
        return ResponseEntity.status(ex.getHttpStatus()).body(new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus()));
    }

    @ExceptionHandler(PatientNullFieldsException.class)
    protected ResponseEntity<MessageDTO> onPatientNullFieldsErrorHandler(PatientNullFieldsException ex){
        return ResponseEntity.status(ex.getHttpStatus()).body(new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus()));
    }

    @ExceptionHandler(PatientIdCardNoChangeException.class)
    protected ResponseEntity<MessageDTO> onPatientIdCardNoChangeErrorHandler(PatientIdCardNoChangeException ex){
        return ResponseEntity.status(ex.getHttpStatus()).body(new MessageDTO(ex.getMessage(), LocalDate.now(), ex.getHttpStatus()));
    }
}
