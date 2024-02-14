package com.rpietraszewski.medicalclinic.model.entity;

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
@Table(name = "INSTITUTION")
@Entity
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME", unique = true)
    private String name;
    @Column(name = "CITY")
    private String city;
    @Column(name = "ZIP_CODE")
    private String zipCode;
    @Column(name = "STREET")
    private String street;
    @Column(name = "BUILDING_NUMBER")
    private String buildingNumber;
    @ManyToMany(mappedBy = "institutions")
    private Set<Doctor> doctors = new HashSet<>();

    public void update(Institution institution) {
        this.name = institution.getName();
        this.city = institution.getCity();
        this.zipCode = institution.getZipCode();
        this.street = institution.getStreet();
        this.buildingNumber = institution.getBuildingNumber();
        this.doctors = institution.getDoctors();
    }
}
