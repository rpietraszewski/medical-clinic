package com.rpietraszewski.medicalclinic.DoctorService;

import com.rpietraszewski.medicalclinic.enums.DoctorSpecialization;
import com.rpietraszewski.medicalclinic.mapper.DoctorMapper;
import com.rpietraszewski.medicalclinic.model.dto.AssignInstitutionCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.DoctorCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.DoctorDTO;
import com.rpietraszewski.medicalclinic.model.entity.Doctor;
import com.rpietraszewski.medicalclinic.model.entity.Institution;
import com.rpietraszewski.medicalclinic.repository.DoctorRepository;
import com.rpietraszewski.medicalclinic.repository.InstitutionRepository;
import com.rpietraszewski.medicalclinic.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

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
        Doctor doctor = Doctor.builder()
                .id(1L)
                .email("doctor@doctor.pl")
                .firstName("doctorName")
                .lastName("doctorLastName")
                .password("doctorPassword")
                .specialization(DoctorSpecialization.GYNECOLOGIST)
                .institutions(new HashSet<>())
                .build();

        when(doctorRepository.findByEmail("doctor@doctor.pl")).thenReturn(Optional.of(doctor));

        DoctorDTO result = doctorService.getDoctor("doctor@doctor.pl");

        assertEquals("doctor@doctor.pl", result.getEmail());
        assertEquals("doctorName", result.getFirstName());
        assertEquals("doctorLastName", result.getLastName());
        assertEquals(DoctorSpecialization.GYNECOLOGIST, result.getSpecialization());
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
    void createDoctor_DataCorrect_DoctorDtoReturned(){
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
    void deleteDoctor_DataCorrect_DoctorDeleted(){
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
    void assignInstitutionToDoctor_DataCorrect_DoctorDtoReturned(){
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
                        .name("name")
                        .build();

        when(doctorRepository.findByEmail("doctor@doctor.pl")).thenReturn(Optional.of(doctor));
        when(institutionRepository.findByName("name")).thenReturn(Optional.of(institution));
        when(doctorRepository.save(any())).thenReturn(doctor);

        DoctorDTO result = doctorService.assignInstitutionToDoctor("doctor@doctor.pl", assignInstitutionCommandDTO);

        assertEquals("doctor@doctor.pl", result.getEmail());
        assertEquals("doctorName", result.getFirstName());
        assertEquals("doctorLastName", result.getLastName());
        assertEquals(DoctorSpecialization.CARDIOLOGIST, result.getSpecialization());
        assertNotNull(result.getInstitutions());
        assertEquals(1, result.getInstitutions().size());
    }
}