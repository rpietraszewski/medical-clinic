package com.rpietraszewski.medicalclinic.validator;

import com.rpietraszewski.medicalclinic.exception.PatientIdCardNoChangeException;
import com.rpietraszewski.medicalclinic.exception.PatientNullFieldsException;
import com.rpietraszewski.medicalclinic.exception.PatientPasswordSameValueException;
import com.rpietraszewski.medicalclinic.model.dto.ChangePasswordCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientCreateDTO;
import com.rpietraszewski.medicalclinic.model.entity.Patient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.rpietraszewski.medicalclinic.MedicalApplicationHelper.patientHasNullFields;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientValidator {
    public static void validatePatient(Patient currentPatient, PatientCreateDTO newPatientData) {
        if (patientHasNullFields(newPatientData)) {
            throw new PatientNullFieldsException("Provided data contain empty values");
        }

        if (!newPatientData.getIdCardNo().equals(currentPatient.getIdCardNo())) {
            throw new PatientIdCardNoChangeException("Cannot change idCardNo");
        }
    }

    public static void validatePasswordChange(Patient existingPatient, ChangePasswordCommandDTO newPassword){
        if (newPassword == null || newPassword.getNewPassword() == null) {
            throw new PatientNullFieldsException("Provided new password cannot be empty");
        }
        if(newPassword.getNewPassword().equals(existingPatient.getPassword())){
            throw new PatientPasswordSameValueException("Password cannot be changed to the same value");
        }
    }
}
