package com.rpietraszewski.medicalclinic.mapper;

import com.rpietraszewski.medicalclinic.model.dto.InstitutionCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.InstitutionDTO;
import com.rpietraszewski.medicalclinic.model.entity.Institution;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {
    InstitutionDTO toInstitutionDTO(Institution institution);

    Institution toInstitution(InstitutionDTO institutionDTO);
    Institution toInstitution(InstitutionCreateDTO institutionCreateDTO);
}
