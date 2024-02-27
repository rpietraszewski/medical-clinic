package com.rpietraszewski.medicalclinic.model.dto;

import com.rpietraszewski.medicalclinic.enums.DoctorSpecialization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DoctorCreateUpdateDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private DoctorSpecialization specialization;
}
