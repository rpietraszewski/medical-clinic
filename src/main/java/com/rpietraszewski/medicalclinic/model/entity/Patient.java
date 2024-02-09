package com.rpietraszewski.medicalclinic.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PATIENT")
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @JsonProperty("id_card_no")
    @Column(name = "ID_CARD_NO", updatable = false)
    private String idCardNo;
    @JsonProperty("first_name")
    @Column(name = "FIRST_NAME")
    private String firstName;
    @JsonProperty("last_name")
    @Column(name = "LAST_NAME")
    private String lastName;
    @JsonProperty("phone_number")
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    public void update(Patient patient) {
        this.email = patient.getEmail();
        this.password = patient.getPassword();
        this.idCardNo = patient.getIdCardNo();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.phoneNumber = patient.getPhoneNumber();
        this.birthday = patient.getBirthday();
    }

}
