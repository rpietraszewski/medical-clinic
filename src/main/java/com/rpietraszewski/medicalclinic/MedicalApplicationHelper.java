package com.rpietraszewski.medicalclinic;

import com.rpietraszewski.medicalclinic.model.dto.PatientCreateDTO;
import com.rpietraszewski.medicalclinic.model.entity.Patient;

import java.util.Objects;
import java.util.stream.Stream;

public class MedicalApplicationHelper {
    public static boolean patientHasNullFields(PatientCreateDTO patient){
        return Stream.of(patient.getEmail(), patient.getPhoneNumber(), patient.getFirstName(), patient.getLastName(), patient.getIdCardNo(), patient.getPassword(), patient.getBirthday())
                .anyMatch(Objects::isNull);
    }
}
