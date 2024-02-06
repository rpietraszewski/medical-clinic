package com.rpietraszewski.medicalclinic.mapper;

import com.rpietraszewski.medicalclinic.model.dto.PatientCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientDTO;
import com.rpietraszewski.medicalclinic.model.entity.Patient;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-06T17:21:02+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class PatientMapperImpl implements PatientMapper {

    @Override
    public Patient PatientDTOToPatient(PatientDTO patientDTO) {
        if ( patientDTO == null ) {
            return null;
        }

        Patient.PatientBuilder patient = Patient.builder();

        patient.email( patientDTO.getEmail() );
        patient.firstName( patientDTO.getFirstName() );
        patient.lastName( patientDTO.getLastName() );

        return patient.build();
    }

    @Override
    public PatientDTO patientToPatientDTO(Patient patient) {
        if ( patient == null ) {
            return null;
        }

        String email = null;
        String firstName = null;
        String lastName = null;

        email = patient.getEmail();
        firstName = patient.getFirstName();
        lastName = patient.getLastName();

        PatientDTO patientDTO = new PatientDTO( email, firstName, lastName );

        return patientDTO;
    }

    @Override
    public Patient patientCreateDTOToPatient(PatientCreateDTO patientCreateDTO) {
        if ( patientCreateDTO == null ) {
            return null;
        }

        Patient.PatientBuilder patient = Patient.builder();

        patient.email( patientCreateDTO.getEmail() );
        patient.password( patientCreateDTO.getPassword() );
        patient.idCardNo( patientCreateDTO.getIdCardNo() );
        patient.firstName( patientCreateDTO.getFirstName() );
        patient.lastName( patientCreateDTO.getLastName() );
        patient.phoneNumber( patientCreateDTO.getPhoneNumber() );
        patient.birthday( patientCreateDTO.getBirthday() );

        return patient.build();
    }
}
