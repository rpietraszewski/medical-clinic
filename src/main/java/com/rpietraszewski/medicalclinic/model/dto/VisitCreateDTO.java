package com.rpietraszewski.medicalclinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class VisitCreateDTO {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long institution;
    private String doctorEmail;
}
