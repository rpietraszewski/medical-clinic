package com.rpietraszewski.medicalclinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InstitutionCreateDTO {
    private String name;
    private String city;
    private String zipCode;
    private String street;
    private String buildingNumber;
}