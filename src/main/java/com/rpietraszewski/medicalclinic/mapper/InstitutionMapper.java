package com.rpietraszewski.medicalclinic.mapper;

import com.rpietraszewski.medicalclinic.model.dto.InstitutionCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.InstitutionDTO;
import com.rpietraszewski.medicalclinic.model.entity.Doctor;
import com.rpietraszewski.medicalclinic.model.entity.Institution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {
    @Mapping(source = "doctors", target = "doctors", qualifiedByName = "mapDoctors")
    InstitutionDTO toInstitutionDTO(Institution institution);

    Institution toInstitution(InstitutionCreateDTO institutionCreateDTO);

    @Named("mapDoctors")
    default Set<Long> mapTolds(Set<Doctor> doctors){
        return doctors.stream()
                .map(Doctor::getId)
                .collect(Collectors.toSet());
    }
}
