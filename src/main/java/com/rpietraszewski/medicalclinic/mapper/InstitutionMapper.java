package com.rpietraszewski.medicalclinic.mapper;

import com.rpietraszewski.medicalclinic.model.dto.InstitutionCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.InstitutionDTO;
import com.rpietraszewski.medicalclinic.model.entity.Institution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {
    @Mapping(target = "doctors", expression = "java(institution.getDoctors().stream().map(d -> d.getId()).toList();)")
    InstitutionDTO toInstitutionDTO(Institution institution);

    Institution toInstitution(InstitutionCreateDTO institutionCreateDTO);
}
