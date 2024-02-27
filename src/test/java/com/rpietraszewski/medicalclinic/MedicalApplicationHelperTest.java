package com.rpietraszewski.medicalclinic;

import com.rpietraszewski.medicalclinic.model.dto.PatientCreateUpdateDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedicalApplicationHelperTest {

    @ParameterizedTest
    @MethodSource("validatePatientData")
    void patientHasNullFields(PatientCreateUpdateDTO patientCreateUpdateDTO, Boolean expectedResult) {

        var result = MedicalApplicationHelper.patientHasNullFields(patientCreateUpdateDTO);

        assertEquals(expectedResult, result);
    }

    static Stream<Arguments> validatePatientData() {
        return Stream.of(
                Arguments.of(PatientCreateUpdateDTO.builder().build(), true),
                Arguments.of(PatientCreateUpdateDTO.builder()
                        .email("patient@patient.pl")
                        .build(), true),
                Arguments.of(PatientCreateUpdateDTO.builder()
                        .email("patient@patient.pl")
                        .phoneNumber("patientPhone")
                        .build(), true),
                Arguments.of(PatientCreateUpdateDTO.builder()
                        .email("patient@patient.pl")
                        .phoneNumber("patientPhone")
                        .firstName("patientName")
                        .build(), true),
                Arguments.of(PatientCreateUpdateDTO.builder()
                        .email("patient@patient.pl")
                        .phoneNumber("patientPhone")
                        .firstName("patientName")
                        .lastName("patientLastName")
                        .build(), true),
                Arguments.of(PatientCreateUpdateDTO.builder()
                        .email("patient@patient.pl")
                        .phoneNumber("patientPhone")
                        .firstName("patientName")
                        .lastName("patientLastName")
                        .idCardNo("idCardNo")
                        .build(), true),
                Arguments.of(PatientCreateUpdateDTO.builder()
                        .email("patient@patient.pl")
                        .phoneNumber("patientPhone")
                        .firstName("patientName")
                        .lastName("patientLastName")
                        .idCardNo("idCardNo")
                        .password("password")
                        .build(), true),
                Arguments.of(PatientCreateUpdateDTO.builder()
                        .email("patient@patient.pl")
                        .phoneNumber("patientPhone")
                        .firstName("patientName")
                        .lastName("patientLastName")
                        .idCardNo("idCardNo")
                        .password("password")
                        .birthday(LocalDate.of(2000, 2, 2))
                        .build(), false)
        );
    }
}
