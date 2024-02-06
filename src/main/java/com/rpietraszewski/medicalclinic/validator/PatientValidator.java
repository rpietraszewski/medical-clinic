package com.rpietraszewski.medicalclinic.validator;

import com.rpietraszewski.medicalclinic.exception.PatientIdCardNoChangeException;
import com.rpietraszewski.medicalclinic.exception.PatientNullFieldsException;
import com.rpietraszewski.medicalclinic.model.dto.PatientCreateDTO;
import com.rpietraszewski.medicalclinic.model.entity.Patient;

import static com.rpietraszewski.medicalclinic.MedicalApplicationHelper.patientHasNullFields;

public class PatientValidator {
    public static void validatePatient(Patient currentPatient, PatientCreateDTO newPatientData){
        if(patientHasNullFields(newPatientData)){
            throw new PatientNullFieldsException("Updated patient cannot have null values");
        }

        if(!newPatientData.getIdCardNo().equals(currentPatient.getIdCardNo())){
            throw new PatientIdCardNoChangeException("Cannot change idCardNo");
        }
    }
}
