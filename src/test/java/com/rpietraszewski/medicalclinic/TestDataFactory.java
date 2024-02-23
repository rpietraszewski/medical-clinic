package com.rpietraszewski.medicalclinic;

import com.rpietraszewski.medicalclinic.enums.DoctorSpecialization;
import com.rpietraszewski.medicalclinic.model.dto.DoctorCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.InstitutionCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.entity.Doctor;
import com.rpietraszewski.medicalclinic.model.entity.Institution;
import com.rpietraszewski.medicalclinic.model.entity.Patient;

import java.time.LocalDate;
import java.util.HashSet;

public class TestDataFactory {
    public static Doctor createDoctor(String email){
        return Doctor.builder()
                .id(1L)
                .email(email)
                .firstName("doctorName")
                .lastName("doctorLastName")
                .password("doctorPassword")
                .specialization(DoctorSpecialization.CARDIOLOGIST)
                .institutions(new HashSet<>())
                .build();
    }

    public static DoctorCreateUpdateDTO createDoctorCreateUpdateDTO(String email){
        return DoctorCreateUpdateDTO.builder()
                .email(email)
                .firstName("doctorName")
                .lastName("doctorLastName")
                .password("doctorPassword")
                .specialization(DoctorSpecialization.CARDIOLOGIST)
                .build();
    }

    public static Patient createPatient(String email, String idCardNo){
        return Patient.builder()
                .id(1L)
                .email(email)
                .firstName("patientName")
                .lastName("patientLastName")
                .idCardNo(idCardNo)
                .password("patientPassword")
                .phoneNumber("patientPhone")
                .birthday(LocalDate.of(2000,5,12))
                .build();
    }

    public static PatientCreateUpdateDTO createPatientCreatUpdateDTO(String email, String idCardNo){
        return PatientCreateUpdateDTO.builder()
                .email(email)
                .firstName("patientNameNew")
                .lastName("patientLastNameNew")
                .idCardNo(idCardNo)
                .password("patientPasswordNew")
                .phoneNumber("patientPhoneNew")
                .birthday(LocalDate.of(2000,5,12))
                .build();
    }

    public static Institution createInstitution(){
        return Institution.builder()
                .id(1L)
                .name("institutionName")
                .city("city")
                .street("street")
                .buildingNumber("buildingNumber")
                .zipCode("zipCode")
                .doctors(new HashSet<>())
                .build();
    }

    public static InstitutionCreateDTO createInstitutionCreateDTO(){
        return InstitutionCreateDTO.builder()
                .name("institutionName")
                .city("city")
                .street("street")
                .buildingNumber("buildingNumber")
                .zipCode("zipCode")
                .build();
    }
}
