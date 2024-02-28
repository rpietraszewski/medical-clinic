package com.rpietraszewski.medicalclinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class FindVisitCommandDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long institution;
    private String doctorEmail;
}
