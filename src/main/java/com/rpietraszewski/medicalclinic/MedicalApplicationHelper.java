package com.rpietraszewski.medicalclinic;

import com.rpietraszewski.medicalclinic.model.dto.PatientCreateUpdateDTO;

import java.util.Objects;
import java.util.stream.Stream;

public class MedicalApplicationHelper {
    public static boolean patientHasNullFields(PatientCreateUpdateDTO patient) {
        return Stream.of(patient.getEmail(), patient.getPhoneNumber(), patient.getFirstName(), patient.getLastName(), patient.getIdCardNo(), patient.getPassword(), patient.getBirthday())
                .anyMatch(Objects::isNull);
    }
}
