package com.rpietraszewski.medicalclinic.mapper;

import com.rpietraszewski.medicalclinic.model.dto.VisitCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.VisitDTO;
import com.rpietraszewski.medicalclinic.model.entity.Doctor;
import com.rpietraszewski.medicalclinic.model.entity.Institution;
import com.rpietraszewski.medicalclinic.model.entity.Patient;
import com.rpietraszewski.medicalclinic.model.entity.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface VisitMapper {
    @Mapping(source = "doctor", target = "doctorEmail", qualifiedByName = "mapToDoctorEmail")
    @Mapping(source = "institution", target = "institution", qualifiedByName = "mapToInstitutionId")
    @Mapping(source = "patient", target = "patientEmail", qualifiedByName = "mapToPatientEmail")
    VisitDTO toVisitDTO(Visit visit);

    @Mapping(source = "institution", target = "institution", ignore = true)
    @Mapping(source = "doctorEmail", target = "doctor", ignore = true)
    Visit toVisit(VisitCreateDTO visitCreateDTO);

    @Named("mapToDoctorEmail")
    default String mapToDoctorEmail(Doctor doctor){
        return doctor.getEmail();
    }

    @Named("mapToInstitutionId")
    default Long mapToInstitutionId(Institution institution){
        return institution.getId();
    }

    @Named("mapToPatientEmail")
    default String mapToPatientEmail(Patient patient){
        return patient.getEmail();
    }
}