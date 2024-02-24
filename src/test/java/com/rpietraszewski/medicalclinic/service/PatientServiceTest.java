package com.rpietraszewski.medicalclinic.service;

import com.rpietraszewski.medicalclinic.TestDataFactory;
import com.rpietraszewski.medicalclinic.exception.PatientEmailAlreadyExistsException;
import com.rpietraszewski.medicalclinic.exception.PatientNotFoundException;
import com.rpietraszewski.medicalclinic.mapper.PatientMapper;
import com.rpietraszewski.medicalclinic.model.dto.ChangePasswordCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientDTO;
import com.rpietraszewski.medicalclinic.model.entity.Patient;
import com.rpietraszewski.medicalclinic.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PatientServiceTest {
    PatientRepository patientRepository;
    PatientMapper patientMapper;
    PatientService patientService;

    @BeforeEach
    void setup(){
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.patientMapper = Mappers.getMapper(PatientMapper.class);
        this.patientService = new PatientService(patientRepository, patientMapper);
    }

    @Test
    void getPatient_DataCorrect_ListPatientsDtoReturned(){
        Patient patient1 = TestDataFactory.createPatient("patient1@patient.pl", "patient1IdCardNo");
        Patient patient2 = TestDataFactory.createPatient("patient2@patient.pl", "patient2IdCardNo");

        when(patientRepository.findAll()).thenReturn(List.of(patient1, patient2));

        List<PatientDTO> result = patientService.getPatients();

        assertEquals(2 , result.size());

        assertEquals("patient1@patient.pl", result.getFirst().getEmail());
        assertEquals("patientName", result.getFirst().getFirstName());
        assertEquals("patientLastName", result.getFirst().getLastName());

        assertEquals("patient2@patient.pl", result.get(1).getEmail());
        assertEquals("patientName", result.get(1).getFirstName());
        assertEquals("patientLastName", result.get(1).getLastName());
    }

    @Test
    void getPatient_DataCorrect_PatientDtoReturned(){
        Patient patient = TestDataFactory.createPatient("patient@patient.pl", "patientIdCardNo");

        when(patientRepository.findByEmail("patient@patient.pl")).thenReturn(Optional.of(patient));

        PatientDTO result = patientService.getPatient("patient@patient.pl");

        assertEquals("patient@patient.pl", result.getEmail());
        assertEquals("patientName", result.getFirstName());
        assertEquals("patientLastName", result.getLastName());
    }

    @Test
    void createPatient_DataCorrect_PatientDtoReturned(){
        Patient patient = TestDataFactory.createPatient("patient@patient.pl", "patientIdCardNo");

        PatientCreateUpdateDTO patientCreateUpdateDTO = TestDataFactory
                .createPatientCreateUpdateDTO("patient@patient.pl","patientIdCardNo");

        when(patientRepository.existsByEmail("patient@patient.pl")).thenReturn(false);
        when(patientRepository.save(any())).thenReturn(patient);

        PatientDTO result = patientService.createPatient(patientCreateUpdateDTO);

        assertEquals("patient@patient.pl", result.getEmail());
        assertEquals("patientName", result.getFirstName());
        assertEquals("patientLastName", result.getLastName());
    }

    @Test
    void patientDelete_DataCorrect_PatientDeleted(){
        Patient patient = TestDataFactory.createPatient("patient@patient.pl", "patientIdCardNo");

        when(patientRepository.findByEmail("patient@patient.pl")).thenReturn(Optional.of(patient));

        patientService.deletePatient("patient@patient.pl");

        verify(patientRepository).delete(patient);
    }

    @Test
    void updatePatient_DataCorrect_PatientDtoReturned(){
        Patient patient = TestDataFactory.createPatient("patient@patient.pl", "patientIdCardNo");

        PatientCreateUpdateDTO patientCreateUpdateDTO = TestDataFactory
                .createPatientCreateUpdateDTO("patient@patient.pl", "patientIdCardNo");

        when(patientRepository.findByEmail("patient@patient.pl")).thenReturn(Optional.of(patient));
        when(patientRepository.save(any())).thenReturn(patient);

        PatientDTO result = patientService.updatePatient("patient@patient.pl", patientCreateUpdateDTO);

        assertEquals("patient@patient.pl", result.getEmail());
        assertEquals("patientName", result.getFirstName());
        assertEquals("patientLastName", result.getLastName());
    }

    @Test
    void updatePassword_DataCorrect_PatientDtoReturned(){
        Patient patient = TestDataFactory.createPatient("patient@patient.pl", "patientIdCardNo");

        ChangePasswordCommandDTO changePasswordCommandDTO = ChangePasswordCommandDTO.builder()
                .newPassword("newPassword")
                .build();

        when(patientRepository.findByEmail("patient@patient.pl")).thenReturn(Optional.of(patient));
        when(patientRepository.save(any())).thenReturn(patient);

        PatientDTO result = patientService.updatePassword("patient@patient.pl", changePasswordCommandDTO);

        assertEquals("newPassword", patient.getPassword());
        verify(patientRepository).save(patient);
    }

    @Test
    void getPatient_PatientNotFound_PatientNotFoundExceptionThrown(){
        //given
        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());

        //when
        PatientNotFoundException exception = Assertions.assertThrows(PatientNotFoundException.class,
                () -> patientService.getPatient("patient@patient.pl"));

        //then
        assertEquals("Patient not found for email patient@patient.pl", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void createPatient_PatientEmailAlreadyExists_PatientEmailAlreadyExistsExceptionThrown(){
        //given
        PatientCreateUpdateDTO patientCreateUpdateDTO = TestDataFactory
                .createPatientCreateUpdateDTO("patient@patient.pl", "patiendIdCardNo");

        when(patientRepository.existsByEmail(any())).thenReturn(true);

        //when
        PatientEmailAlreadyExistsException exception = Assertions.assertThrows(PatientEmailAlreadyExistsException.class,
                () -> patientService.createPatient(patientCreateUpdateDTO));

        //then
        assertEquals("Email already exists for email patient@patient.pl", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
    }

    @Test
    void deletePatient_PatientNotFound_PatientNotFoundExceptionThrown(){
        //given
        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());

        //when
        PatientNotFoundException exception = Assertions.assertThrows(PatientNotFoundException.class,
                () -> patientService.deletePatient("patient@patien.pl"));

        //then
        assertEquals("Patient not found for email patient@patien.pl", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void updatePatient_PatientNotFound_PatientNotFoundExceptionThrown(){
        //given
        PatientCreateUpdateDTO patientCreateUpdateDTO = TestDataFactory
                .createPatientCreateUpdateDTO("patient@patient.pl", "patientIdCardNo");

        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());

        //when
        PatientNotFoundException exception = Assertions.assertThrows(PatientNotFoundException.class,
                () -> patientService.updatePatient("patient@patient.pl", patientCreateUpdateDTO));

        //then
        assertEquals("Patient not found for email patient@patient.pl", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void updatePassword_PatientNotFound_PatientNotFoundExceptionThrown(){
        //given
        ChangePasswordCommandDTO changePasswordCommandDTO = ChangePasswordCommandDTO.builder()
                .newPassword("newPassword")
                .build();

        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());

        //when
        PatientNotFoundException exception = Assertions.assertThrows(PatientNotFoundException.class,
                () -> patientService.updatePassword("patient@patient.pl", changePasswordCommandDTO));

        //then
        assertEquals("Patient not found for email patient@patient.pl", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}
