package com.rpietraszewski.medicalclinic.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Set<Doctor> doctors = new HashSet<>();

    public void update(Institution institution) {
        this.name = institution.getName();
        this.city = institution.getCity();
        this.zipCode = institution.getZipCode();
        this.street = institution.getStreet();
        this.buildingNumber = institution.getBuildingNumber();
        this.doctors = institution.getDoctors();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Institution)) {
            return false;
        }

        Institution other = (Institution) o;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Institution{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", street='" + street + '\'' +
                ", buildingNumber='" + buildingNumber + '\'' +
                ", doctors=" + getDoctors().stream()
                .map(Doctor::getId).toList() +
                '}';
    }
}
