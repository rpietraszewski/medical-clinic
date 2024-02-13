package com.rpietraszewski.medicalclinic.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class AssignInstitutionCommandDTO {
    private String name;
}
