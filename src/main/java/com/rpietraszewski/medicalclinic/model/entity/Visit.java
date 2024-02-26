package com.rpietraszewski.medicalclinic.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "VISIT")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @ManyToOne
    @JoinColumn(name = "institution_id")
    @JsonIgnore
    private Institution institution;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;
}
