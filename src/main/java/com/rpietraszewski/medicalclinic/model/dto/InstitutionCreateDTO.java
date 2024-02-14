package com.rpietraszewski.medicalclinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class InstitutionCreateDTO {
    private String name;
    private String city;
    private String zipCode;
    private String street;
    private String buildingNumber;
    private Set<DoctorDTO> doctors;
}
