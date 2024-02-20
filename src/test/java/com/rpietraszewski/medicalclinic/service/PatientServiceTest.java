package com.rpietraszewski.medicalclinic.service;

import com.rpietraszewski.medicalclinic.mapper.PatientMapper;
import com.rpietraszewski.medicalclinic.model.dto.ChangePasswordCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientDTO;
import com.rpietraszewski.medicalclinic.model.entity.Patient;
import com.rpietraszewski.medicalclinic.repository.PatientRepository;
import com.rpietraszewski.medicalclinic.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.time.LocalDate;
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
        Patient patient1 = Patient.builder()
                .id(1L)
                .email("patient1@patient.pl")
                .firstName("patientName1")
                .lastName("patientLastName1")
                .idCardNo("patientCard1")
                .password("patientPassword1")
                .phoneNumber("patientPhone1")
                .birthday(LocalDate.of(2000,5,6))
                .build();

        Patient patient2 = Patient.builder()
                .id(2L)
                .email("patient2@patient.pl")
                .firstName("patientName2")
                .lastName("patientLastName2")
                .idCardNo("patientCard2")
                .password("patientPassword2")
                .phoneNumber("patientPhone2")
                .birthday(LocalDate.of(1999,4,11))
                .build();

        when(patientRepository.findAll()).thenReturn(List.of(patient1, patient2));

        List<PatientDTO> result = patientService.getPatients();

        assertEquals(2 , result.size());

        assertEquals("patient1@patient.pl", result.get(0).getEmail());
        assertEquals("patientName1", result.get(0).getFirstName());
        assertEquals("patientLastName1", result.get(0).getLastName());

        assertEquals("patient2@patient.pl", result.get(1).getEmail());
        assertEquals("patientName2", result.get(1).getFirstName());
        assertEquals("patientLastName2", result.get(1).getLastName());
    }

    @Test
    void getPatient_DataCorrect_PatientDtoReturned(){
        Patient patient = Patient.builder()
                .id(1L)
                .email("patient@patient.pl")
                .firstName("patientName")
                .lastName("patientLastName")
                .idCardNo("patientCard")
                .password("patientPassword")
                .phoneNumber("patientPhone")
                .birthday(LocalDate.of(2000,5,6))
                .build();

        when(patientRepository.findByEmail("patient@patient.pl")).thenReturn(Optional.of(patient));

        PatientDTO result = patientService.getPatient("patient@patient.pl");

        assertEquals("patient@patient.pl", result.getEmail());
        assertEquals("patientName", result.getFirstName());
        assertEquals("patientLastName", result.getLastName());
    }

    @Test
    void createPatient_DataCorrect_PatientDtoReturned(){
        Patient patient = Patient.builder()
                .id(1L)
                .email("patient@patient.pl")
                .firstName("patientName")
                .lastName("patientLastName")
                .idCardNo("patientCard")
                .password("patientPassword")
                .phoneNumber("patientPhone")
                .birthday(LocalDate.of(2000,5,6))
                .build();

        PatientCreateUpdateDTO patientCreateUpdateDTO = PatientCreateUpdateDTO.builder()
                .email("patient@patient.pl")
                .firstName("patientName")
                .lastName("patientLastName")
                .idCardNo("patientCard")
                .password("patientPassword")
                .phoneNumber("patientPhone")
                .birthday(LocalDate.of(2000,5,6))
                .build();

        when(patientRepository.existsByEmail("patient@patient.pl")).thenReturn(false);
        when(patientRepository.save(any())).thenReturn(patient);

        PatientDTO result = patientService.createPatient(patientCreateUpdateDTO);

        assertEquals("patient@patient.pl", result.getEmail());
        assertEquals("patientName", result.getFirstName());
        assertEquals("patientLastName", result.getLastName());
    }

    @Test
    void patientDelete_DataCorrect_PatientDeleted(){
        Patient patient = Patient.builder()
                .id(1L)
                .email("patient@patient.pl")
                .firstName("patientName")
                .lastName("patientLastName")
                .idCardNo("patientCard")
                .password("patientPassword")
                .phoneNumber("patientPhone")
                .birthday(LocalDate.of(2000,5,6))
                .build();

        when(patientRepository.findByEmail("patient@patient.pl")).thenReturn(Optional.of(patient));

        patientService.deletePatient("patient@patient.pl");

        verify(patientRepository).delete(patient);
    }

    @Test
    void updatePatient_DataCorrect_PatientDtoReturned(){
        Patient patient = Patient.builder()
                .id(1L)
                .email("patient@patient.pl")
                .firstName("patientName")
                .lastName("patientLastName")
                .idCardNo("patientCard")
                .password("patientPassword")
                .phoneNumber("patientPhone")
                .birthday(LocalDate.of(2000,5,6))
                .build();

        PatientCreateUpdateDTO patientCreateUpdateDTO = PatientCreateUpdateDTO.builder()
                .email("patient2@patient.pl")
                .firstName("patientName2")
                .lastName("patientLastName2")
                .idCardNo("patientCard")
                .password("patientPassword")
                .phoneNumber("patientPhone")
                .birthday(LocalDate.of(2000,5,6))
                .build();

        when(patientRepository.findByEmail("patient@patient.pl")).thenReturn(Optional.of(patient));
        when(patientRepository.save(any())).thenReturn(patient);

        PatientDTO result = patientService.updatePatient("patient@patient.pl", patientCreateUpdateDTO);

        assertEquals("patient2@patient.pl", result.getEmail());
        assertEquals("patientName2", result.getFirstName());
        assertEquals("patientLastName2", result.getLastName());
    }

    @Test
    void updatePassword_DataCorrect_PatientDtoReturned(){
        Patient patient = Patient.builder()
                .id(1L)
                .email("patient@patient.pl")
                .firstName("patientName")
                .lastName("patientLastName")
                .idCardNo("patientCard")
                .password("patientPassword")
                .phoneNumber("patientPhone")
                .birthday(LocalDate.of(2000,5,6))
                .build();

        ChangePasswordCommandDTO changePasswordCommandDTO = ChangePasswordCommandDTO.builder()
                .newPassword("newPassword")
                .build();

        when(patientRepository.findByEmail("patient@patient.pl")).thenReturn(Optional.of(patient));
        when(patientRepository.save(any())).thenReturn(patient);

        PatientDTO result = patientService.updatePassword("patient@patient.pl", changePasswordCommandDTO);


        verify(patientRepository).save(patient);
    }
}
