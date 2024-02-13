package com.rpietraszewski.medicalclinic.mapper;

import com.rpietraszewski.medicalclinic.model.dto.InstitutionCRUDDTO;
import com.rpietraszewski.medicalclinic.model.entity.Institution;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {
    InstitutionCRUDDTO institutionToInstitutionCRUDDTO(Institution institution);

    Institution institutionCRUDDTOToInstitution(InstitutionCRUDDTO institutionCRUDDTO);
}
