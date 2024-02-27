package com.rpietraszewski.medicalclinic.model.dto;

import lombok.*;

@Getter
@EqualsAndHashCode
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ChangePasswordCommandDTO {
    private String newPassword;
}
