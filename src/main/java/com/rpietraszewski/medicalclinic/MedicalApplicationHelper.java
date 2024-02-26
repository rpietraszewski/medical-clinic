package com.rpietraszewski.medicalclinic;

import com.rpietraszewski.medicalclinic.model.dto.PatientCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.VisitCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.VisitDTO;

import java.util.Objects;
import java.util.stream.Stream;

public class MedicalApplicationHelper {
    public static boolean patientHasNullFields(PatientCreateUpdateDTO patient) {
        return Stream.of(patient.getEmail(), patient.getPhoneNumber(), patient.getFirstName(), patient.getLastName(), patient.getIdCardNo(), patient.getPassword(), patient.getBirthday())
                .anyMatch(Objects::isNull);
    }

    public static boolean visitHasNullFields(VisitCreateDTO visit){
        return Stream.of(visit.getDoctorEmail(), visit.getInstitution(), visit.getStartDateTime(), visit.getEndDateTime())
                .anyMatch(Objects::isNull);
    }
}
