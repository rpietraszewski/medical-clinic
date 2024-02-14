package com.rpietraszewski.medicalclinic.model.entity;

import com.rpietraszewski.medicalclinic.enums.DoctorSpecialization;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DOCTOR")
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "EMAIL", unique = true)
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "SPECIALIZATION")
    private DoctorSpecialization specialization;
    @ManyToMany
    @JoinTable(
            name = "DOCTOR_INSTITUTION",
            joinColumns = @JoinColumn(name = "DOCTOR_ID"),
            inverseJoinColumns = @JoinColumn(name = "INSTITUTION_ID"))
    private Set<Institution> institutions = new HashSet<>();

    public void update(Doctor doctor) {
        this.email = doctor.getEmail();
        this.password = doctor.getPassword();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.specialization = doctor.getSpecialization();
        this.institutions = doctor.getInstitutions();
    }
}
