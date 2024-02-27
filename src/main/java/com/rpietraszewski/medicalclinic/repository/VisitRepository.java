package com.rpietraszewski.medicalclinic.repository;

import com.rpietraszewski.medicalclinic.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    @Query("SELECT v FROM Visit v " +
            "WHERE v.doctor.email = :doctorEmail " +
            "AND v.startDateTime <= :endTime " +
            "AND v.endDateTime >= :startTime")
    boolean existsByDoctorAndStartTimeAndEndTime(String doctorEmail, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT v FROM Visit v " +
            "WHERE v.doctor.email = :doctorEmail " +
            "AND v.startDateTime >= :startTime " +
            "AND v.endDateTime <= :endTime " +
            "AND v.patient = IS NULL")
    List<Visit> findByAvailableDoctorAndStartTimeAndEndTime(String doctorEmail, LocalDateTime startTime, LocalDateTime endTime);
}
