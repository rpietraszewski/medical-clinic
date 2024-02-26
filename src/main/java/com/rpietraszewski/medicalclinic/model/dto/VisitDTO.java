package com.rpietraszewski.medicalclinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class VisitDTO {
    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long institution;
    private String doctorEmail;
    private String patientEmail;
}
