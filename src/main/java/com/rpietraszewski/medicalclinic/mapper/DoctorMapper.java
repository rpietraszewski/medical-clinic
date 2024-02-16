package com.rpietraszewski.medicalclinic.mapper;

import com.rpietraszewski.medicalclinic.model.dto.DoctorCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.DoctorDTO;
import com.rpietraszewski.medicalclinic.model.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    @Mapping(target = "institutions", expression = "java(doctor.getInstitutions().stream().map(i -> i.getId()).toList();)")
    DoctorDTO toDoctorDTO(Doctor doctor);

    Doctor toDoctor(DoctorCreateUpdateDTO doctorCreateUpdateDTO);
}
