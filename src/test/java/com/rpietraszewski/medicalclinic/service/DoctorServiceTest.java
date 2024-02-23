package com.rpietraszewski.medicalclinic.service;

import com.rpietraszewski.medicalclinic.TestDataFactory;
import com.rpietraszewski.medicalclinic.enums.DoctorSpecialization;
import com.rpietraszewski.medicalclinic.exception.DoctorEmailAlreadyExistsException;
import com.rpietraszewski.medicalclinic.exception.DoctorNotFoundException;
import com.rpietraszewski.medicalclinic.exception.InstitutionNotFoundException;
import com.rpietraszewski.medicalclinic.mapper.DoctorMapper;
import com.rpietraszewski.medicalclinic.model.dto.AssignInstitutionCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.DoctorCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.DoctorDTO;
import com.rpietraszewski.medicalclinic.model.entity.Doctor;
import com.rpietraszewski.medicalclinic.model.entity.Institution;
import com.rpietraszewski.medicalclinic.repository.DoctorRepository;
import com.rpietraszewski.medicalclinic.repository.InstitutionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DoctorServiceTest {
    DoctorService doctorService;
    DoctorRepository doctorRepository;
    InstitutionRepository institutionRepository;
    DoctorMapper doctorMapper;

    @BeforeEach
    void setup() {
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.institutionRepository = Mockito.mock(InstitutionRepository.class);
        this.doctorMapper = Mappers.getMapper(DoctorMapper.class);
        this.doctorService = new DoctorService(doctorRepository, institutionRepository, doctorMapper);
    }

    @Test
    void getDoctor_DataCorrect_DoctorDtoReturned() {
        Doctor doctor = TestDataFactory.createDoctor("doctor@doctor.pl");

        when(doctorRepository.findByEmail("doctor@doctor.pl")).thenReturn(Optional.of(doctor));

        DoctorDTO result = doctorService.getDoctor("doctor@doctor.pl");

        assertEquals("doctor@doctor.pl", result.getEmail());
        assertEquals("doctorName", result.getFirstName());
        assertEquals("doctorLastName", result.getLastName());
        assertEquals(DoctorSpecialization.CARDIOLOGIST, result.getSpecialization());
        assertTrue(result.getInstitutions().isEmpty());
    }

    @Test
    void getDoctors_DataCorrect_ListDoctorDtoReturned() {
        Doctor doctorFirst = Doctor.builder()
                .id(1L)
                .email("doctor1@doctor.pl")
                .firstName("doctorName1")
                .lastName("doctorLastName1")
                .password("doctorPassword1")
                .specialization(DoctorSpecialization.GYNECOLOGIST)
                .institutions(new HashSet<>())
                .build();

        Doctor doctorSecond = Doctor.builder()
                .id(2L)
                .email("doctor2@doctor.pl")
                .firstName("doctorName2")
                .lastName("doctorLastName2")
                .password("doctorPassword2")
                .specialization(DoctorSpecialization.DENTIST)
                .institutions(new HashSet<>())
                .build();

        List<Doctor> doctors = List.of(doctorFirst, doctorSecond);

        when(doctorRepository.findAll()).thenReturn(doctors);

        List<DoctorDTO> doctorsDTO = doctorService.getDoctors();

        assertEquals(doctors.size(), doctorsDTO.size());

        assertEquals("doctor1@doctor.pl", doctorsDTO.get(0).getEmail());
        assertEquals("doctorName1", doctorsDTO.get(0).getFirstName());
        assertEquals("doctorLastName1", doctorsDTO.get(0).getLastName());
        assertEquals(DoctorSpecialization.GYNECOLOGIST, doctorsDTO.get(0).getSpecialization());
        assertTrue(doctorsDTO.get(0).getInstitutions().isEmpty());

        assertEquals("doctor2@doctor.pl", doctorsDTO.get(1).getEmail());
        assertEquals("doctorName2", doctorsDTO.get(1).getFirstName());
        assertEquals("doctorLastName2", doctorsDTO.get(1).getLastName());
        assertEquals(DoctorSpecialization.DENTIST, doctorsDTO.get(1).getSpecialization());
        assertTrue(doctorsDTO.get(1).getInstitutions().isEmpty());
    }

    @Test
    void createDoctor_DataCorrect_DoctorDtoReturned() {
        DoctorCreateUpdateDTO doctorCreateUpdateDTO = DoctorCreateUpdateDTO.builder()
                .email("doctor@doctor.pl")
                .firstName("doctorName")
                .lastName("doctorLastName")
                .password("doctorPassword")
                .specialization(DoctorSpecialization.CARDIOLOGIST)
                .build();

        Doctor doctor = Doctor.builder()
                .email("doctor@doctor.pl")
                .firstName("doctorName")
                .lastName("doctorLastName")
                .password("doctorPassword")
                .specialization(DoctorSpecialization.CARDIOLOGIST)
                .institutions(new HashSet<>())
                .build();

        when(doctorRepository.existsByEmail("doctor@doctor.pl")).thenReturn(false);
        when(doctorRepository.save(any())).thenReturn(doctor);

        DoctorDTO result = doctorService.createDoctor(doctorCreateUpdateDTO);

        assertEquals("doctor@doctor.pl", result.getEmail());
        assertEquals("doctorName", result.getFirstName());
        assertEquals("doctorLastName", result.getLastName());
        assertEquals(DoctorSpecialization.CARDIOLOGIST, result.getSpecialization());
        assertTrue(result.getInstitutions().isEmpty());
    }

    @Test
    void deleteDoctor_DataCorrect_DoctorDeleted() {
        Doctor doctor = Doctor.builder()
                .email("doctor@doctor.pl")
                .firstName("doctorName")
                .lastName("doctorLastName")
                .password("doctorPassword")
                .specialization(DoctorSpecialization.CARDIOLOGIST)
                .institutions(new HashSet<>())
                .build();

        when(doctorRepository.findByEmail("doctor@doctor.pl")).thenReturn(Optional.of(doctor));

        doctorService.deleteDoctor("doctor@doctor.pl");

        verify(doctorRepository).delete(doctor);
    }

    @Test
    void assignInstitutionToDoctor_DataCorrect_DoctorDtoReturned() {
        Doctor doctor = Doctor.builder()
                .email("doctor@doctor.pl")
                .firstName("doctorName")
                .lastName("doctorLastName")
                .password("doctorPassword")
                .specialization(DoctorSpecialization.CARDIOLOGIST)
                .institutions(new HashSet<>())
                .build();

        Institution institution = Institution.builder()
                .id(1L)
                .city("city")
                .name("name")
                .street("street")
                .zipCode("zipCode")
                .buildingNumber("number")
                .build();

        AssignInstitutionCommandDTO assignInstitutionCommandDTO = AssignInstitutionCommandDTO.builder()
                .id(1L)
                .build();

        when(doctorRepository.findByEmail("doctor@doctor.pl")).thenReturn(Optional.of(doctor));
        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));
        when(doctorRepository.save(any())).thenReturn(doctor);

        DoctorDTO result = doctorService.assignInstitutionToDoctor("doctor@doctor.pl", assignInstitutionCommandDTO);

        assertEquals("doctor@doctor.pl", result.getEmail());
        assertEquals("doctorName", result.getFirstName());
        assertEquals("doctorLastName", result.getLastName());
        assertEquals(DoctorSpecialization.CARDIOLOGIST, result.getSpecialization());
        assertNotNull(result.getInstitutions());
        assertEquals(1, result.getInstitutions().size());
    }

    @Test
    void getDoctor_DoctorNotFound_DoctorNotFoundExceptionThrown() {
        //given
        when(doctorRepository.findByEmail(any())).thenReturn(Optional.empty());

        //when
        DoctorNotFoundException exception = Assertions.assertThrows(DoctorNotFoundException.class,
                () -> doctorService.getDoctor("doctor@doctor.pl"));

        //then
        assertEquals("Doctor not found for email doctor@doctor.pl", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void createDoctor_DoctorEmailAlreadyExists_DoctorEmailAlreadyExistsExceptionThrown() {
        //given
        DoctorCreateUpdateDTO doctorCreateUpdateDTO = TestDataFactory.createDoctorCreateUpdateDTO("doctor@doctor.pl");

        when(doctorRepository.existsByEmail(any())).thenReturn(true);

        //when
        DoctorEmailAlreadyExistsException exception = Assertions.assertThrows(DoctorEmailAlreadyExistsException.class,
                () -> doctorService.createDoctor(doctorCreateUpdateDTO));

        //then
        assertEquals("Email already exists for email doctor@doctor.pl", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
    }

    @Test
    void deleteDoctor_DoctorNotFound_DoctorNotFoundExceptionThrown(){
        //given
        when(doctorRepository.findByEmail(any())).thenReturn(Optional.empty());

        //when
        DoctorNotFoundException exception = Assertions.assertThrows(DoctorNotFoundException.class,
                () -> doctorService.deleteDoctor("doctor@doctor.pl"));

        //then
        assertEquals("Doctor not found for email doctor@doctor.pl", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void assignInstitutionToDoctor_DoctorNotFound_DoctorNotFoundExceptionThrown() {
        //given
        AssignInstitutionCommandDTO assignInstitutionCommandDTO = new AssignInstitutionCommandDTO(1L);

        when(doctorRepository.findByEmail(any())).thenReturn(Optional.empty());

        //when
        DoctorNotFoundException exception = Assertions.assertThrows(DoctorNotFoundException.class,
                () -> doctorService.assignInstitutionToDoctor("doctor@doctor.pl", assignInstitutionCommandDTO));

        //then
        assertEquals("Doctor not found for email doctor@doctor.pl", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void assignInstitutionToDoctor_InstitutionNotFound_InstitutionNotFoundExceptionThrown(){
        //given
        AssignInstitutionCommandDTO assignInstitutionCommandDTO = new AssignInstitutionCommandDTO(1L);
        Doctor doctor = TestDataFactory.createDoctor("doctor@doctor.pl");

        when(doctorRepository.findByEmail(any())).thenReturn(Optional.of(doctor));
        when(institutionRepository.findByName(any())).thenReturn(Optional.empty());

        //when
        InstitutionNotFoundException exception = Assertions.assertThrows(InstitutionNotFoundException.class,
                () -> doctorService.assignInstitutionToDoctor("doctor@doctor.pl", assignInstitutionCommandDTO));

        //then
        assertEquals("Institution not found for id 1", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}