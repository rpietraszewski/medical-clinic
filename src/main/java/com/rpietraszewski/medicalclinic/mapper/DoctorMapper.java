package com.rpietraszewski.medicalclinic.mapper;

import com.rpietraszewski.medicalclinic.model.dto.DoctorCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.DoctorDTO;
import com.rpietraszewski.medicalclinic.model.entity.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDTO doctorToDoctorDTO(Doctor doctor);

    Doctor doctorCreateUpdateDTOToDoctor(DoctorCreateUpdateDTO doctorCreateUpdateDTO);
}
