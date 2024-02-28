package com.rpietraszewski.medicalclinic.mapper;

import com.rpietraszewski.medicalclinic.model.dto.VisitCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.VisitDTO;
import com.rpietraszewski.medicalclinic.model.entity.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VisitMapper {
    @Mapping(source = "doctor.email", target = "doctorEmail")
    @Mapping(source = "institution.id", target = "institution")
    @Mapping(source = "patient.email", target = "patientEmail")
    VisitDTO toVisitDTO(Visit visit);

    @Mapping(source = "institution", target = "institution", ignore = true)
    @Mapping(source = "doctorEmail", target = "doctor", ignore = true)
    Visit toVisit(VisitCreateDTO visitCreateDTO);
}