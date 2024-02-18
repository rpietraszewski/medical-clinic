package com.rpietraszewski.medicalclinic.model.dto;

import com.rpietraszewski.medicalclinic.enums.DoctorSpecialization;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class DoctorDTO {
    private String email;
    private String firstName;
    private String lastName;
    private DoctorSpecialization specialization;
    private Set<Long> institutions;
}
