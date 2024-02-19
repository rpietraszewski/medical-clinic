package com.rpietraszewski.medicalclinic.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rpietraszewski.medicalclinic.enums.DoctorSpecialization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "doctor_institution",
            joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "institution_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Institution> institutions = new HashSet<>();

    public void update(Doctor doctor) {
        this.email = doctor.getEmail();
        this.password = doctor.getPassword();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.specialization = doctor.getSpecialization();
        this.institutions = doctor.getInstitutions();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Doctor)) {
            return false;
        }

        Doctor other = (Doctor) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialization='" + specialization + '\'' +
                ", institutions=" + getInstitutions().stream()
                .map(Institution::getId).toList() +
                '}';
    }
}
