package com.rpietraszewski.medicalclinic.model.dto;

import lombok.*;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ChangePasswordCommandDTO {
    private String newPassword;
}
