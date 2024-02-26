package com.rpietraszewski.medicalclinic.validator;

import com.rpietraszewski.medicalclinic.exception.VisitNullFieldsException;
import com.rpietraszewski.medicalclinic.exception.VisitWrongDateTimeException;
import com.rpietraszewski.medicalclinic.model.dto.VisitCreateDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.rpietraszewski.medicalclinic.MedicalApplicationHelper.visitHasNullFields;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VisitValidator {
    public static void validateVisit(VisitCreateDTO visitCreateDTO){
        if(visitHasNullFields(visitCreateDTO)){
            throw new VisitNullFieldsException("Visit cannot contain empty values");
        }
        if(visitCreateDTO.getStartDateTime().isBefore(LocalDateTime.now())){
            throw new VisitWrongDateTimeException("Visit start date cannot be in the past");
        }
        if(visitCreateDTO.getStartDateTime().getMinute() % 15 != 0){
            throw new IllegalArgumentException("Visit must start at some quarter of an hour");
        }
        if(visitCreateDTO.getEndDateTime().isBefore(visitCreateDTO.getEndDateTime())){
            throw new IllegalArgumentException("Visit end date cannot be earlier than start date");
        }
    }
}
