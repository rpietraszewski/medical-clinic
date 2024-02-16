package com.rpietraszewski.medicalclinic.model.dto;

import com.rpietraszewski.medicalclinic.enums.DoctorSpecialization;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DoctorDTO {
    private String email;
    private String firstName;
    private String lastName;
    private DoctorSpecialization specialization;
    private List<Long> institutions;
}
