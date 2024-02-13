package com.rpietraszewski.medicalclinic.mapper;

import com.rpietraszewski.medicalclinic.model.dto.PatientCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientDTO;
import com.rpietraszewski.medicalclinic.model.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDTO patientToPatientDTO(Patient patient);

    Patient patientCreateUpdateDTOToPatient(PatientCreateUpdateDTO patientCreateUpdateDTO);
}
