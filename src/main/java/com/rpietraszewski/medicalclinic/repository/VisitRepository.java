package com.rpietraszewski.medicalclinic.repository;

import com.rpietraszewski.medicalclinic.model.entity.Doctor;
import com.rpietraszewski.medicalclinic.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    @Query("Select ")
    List<Visit> findByDoctorAndStartTimeAndEndTime(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime);
}
