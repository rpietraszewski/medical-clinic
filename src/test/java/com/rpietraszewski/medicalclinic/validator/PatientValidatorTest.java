package com.rpietraszewski.medicalclinic.validator;

import com.rpietraszewski.medicalclinic.TestDataFactory;
import com.rpietraszewski.medicalclinic.exception.PatientIdCardNoChangeException;
import com.rpietraszewski.medicalclinic.exception.PatientNullFieldsException;
import com.rpietraszewski.medicalclinic.exception.PatientPasswordSameValueException;
import com.rpietraszewski.medicalclinic.model.dto.ChangePasswordCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.entity.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientValidatorTest {

    @Test
    void validatePatient_PatientNullFields_PatientNullFieldsExceptionThrown() {
        //given
        PatientCreateUpdateDTO patientCreateUpdateDTO = PatientCreateUpdateDTO.builder().build();

        Patient patient = Patient.builder().build();

        //when
        PatientNullFieldsException exception = Assertions.assertThrows(PatientNullFieldsException.class,
                () -> PatientValidator.validatePatient(patient, patientCreateUpdateDTO));

        //then
        assertEquals("Provided data contain empty values", exception.getMessage());
    }

    @Test
    void validatePatient_PatientIdCardNoChange_PatientIdCardNoChangeExceptionThrown() {
        //given
        PatientCreateUpdateDTO patientCreateUpdateDTO = TestDataFactory
                .createPatientCreateUpdateDTO("patient@patient.pl", "idCardNo");

        Patient patient = TestDataFactory
                .createPatient("patient@patient.pl", "idCardNo2");

        //when
        PatientIdCardNoChangeException exception = Assertions.assertThrows(PatientIdCardNoChangeException.class,
                () -> PatientValidator.validatePatient(patient, patientCreateUpdateDTO));

        //then
        assertEquals("Cannot change idCardNo", exception.getMessage());
    }

    @Test
    void validatePasswordChange_PatientNullFields_PatientNullFieldsExceptionThrown() {
        //given
        Patient patient = Patient.builder().build();

        ChangePasswordCommandDTO changePasswordCommandDTO = ChangePasswordCommandDTO.builder().build();

        //when
        PatientNullFieldsException exception = Assertions.assertThrows(PatientNullFieldsException.class,
                () -> PatientValidator.validatePasswordChange(patient, changePasswordCommandDTO));

        //then
        assertEquals("Provided new password cannot be empty", exception.getMessage());
    }

    @Test
    void validatePasswordChange_PatientPasswordSameValue_PatientPasswordSameValueExceptionThrown() {
        //given
        Patient patient = TestDataFactory.createPatient("patient@patient.pl", "idCardNo");

        ChangePasswordCommandDTO changePasswordCommandDTO = ChangePasswordCommandDTO.builder()
                .newPassword("patientPassword")
                .build();

        //when
        PatientPasswordSameValueException exception = Assertions.assertThrows(PatientPasswordSameValueException.class,
                () -> PatientValidator.validatePasswordChange(patient, changePasswordCommandDTO));

        //then
        assertEquals("Password cannot be changed to the same value", exception.getMessage());
    }
}
