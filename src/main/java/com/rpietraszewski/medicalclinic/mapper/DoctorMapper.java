package com.rpietraszewski.medicalclinic.mapper;

import com.rpietraszewski.medicalclinic.model.dto.DoctorCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.DoctorDTO;
import com.rpietraszewski.medicalclinic.model.entity.Doctor;
import com.rpietraszewski.medicalclinic.model.entity.Institution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    @Mapping(source = "institutions", target = "institutions", qualifiedByName = "mapInstitutions")
    DoctorDTO toDoctorDTO(Doctor doctor);

    Doctor toDoctor(DoctorCreateUpdateDTO doctorCreateUpdateDTO);

    @Named("mapInstitutions")
    default Set<Long> mapToIds(Set<Institution> institutions){
        return institutions.stream()
                .map(Institution::getId)
                .collect(Collectors.toSet());
    }
}
