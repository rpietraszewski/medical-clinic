package com.rpietraszewski.medicalclinic.repository;

import com.rpietraszewski.medicalclinic.model.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    Optional<Institution> findByName(String name);

    Boolean existsByName(String name);
}
