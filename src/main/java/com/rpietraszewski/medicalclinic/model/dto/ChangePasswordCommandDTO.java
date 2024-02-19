package com.rpietraszewski.medicalclinic.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(force = true)
public class ChangePasswordCommandDTO {
    private String newPassword;
}
