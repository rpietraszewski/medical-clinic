package com.rpietraszewski.medicalclinic.mapper;

import com.rpietraszewski.medicalclinic.model.dto.PatientCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientDTO;
import com.rpietraszewski.medicalclinic.model.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    Patient PatientDTOToPatient(PatientDTO patientDTO);

    PatientDTO patientToPatientDTO(Patient patient);

    Patient patientCreateDTOToPatient(PatientCreateDTO patientCreateDTO);
}
