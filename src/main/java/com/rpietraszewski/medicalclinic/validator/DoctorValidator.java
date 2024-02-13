package com.rpietraszewski.medicalclinic.validator;

import com.rpietraszewski.medicalclinic.exception.DoctorInstitutionAlreadyAssignedException;
import com.rpietraszewski.medicalclinic.exception.DoctorNullFieldsException;
import com.rpietraszewski.medicalclinic.model.dto.AssignInstitutionCommandDTO;
import com.rpietraszewski.medicalclinic.model.entity.Doctor;
import com.rpietraszewski.medicalclinic.model.entity.Institution;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DoctorValidator {
    public static void validateNullAssignInstitution(AssignInstitutionCommandDTO institution) {
        if (institution == null || institution.getName() == null) {
            throw new DoctorNullFieldsException("Provided institution name cannot be null");
        }
    }

    public static void validateAssignInstitutionExists(Doctor existingDoctor, Institution institution) {
        if (existingDoctor.getInstitutions().contains(institution)) {
            throw new DoctorInstitutionAlreadyAssignedException("Given Doctor has already this institution");
        }
    }
}
