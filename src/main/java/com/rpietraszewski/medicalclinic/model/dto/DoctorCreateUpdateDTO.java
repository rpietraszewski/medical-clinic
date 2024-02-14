package com.rpietraszewski.medicalclinic.model.dto;

import com.rpietraszewski.medicalclinic.enums.DoctorSpecialization;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class DoctorCreateUpdateDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private DoctorSpecialization specialization;
    private Set<InstitutionDTO> institutions;
}
