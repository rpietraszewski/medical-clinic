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
    List<Visit> existsByDoctorAndStartTimeAndEndTime(String doctorEmail, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT v FROM Visit v " +
            "WHERE v.doctor.id = :doctorId " +
            "AND v.startDateTime >= :startTime " +
            "AND v.endDateTime <= :endTime " +
            "AND v.patient IS NULL " +
            "AND v.institution.id = :institutionId")
    List<Visit> findByAvailableDoctorAndStartTimeAndEndTime(Long doctorId, LocalDateTime startTime, LocalDateTime endTime, Long institutionId);

    @Query("SELECT v FROM Visit v " +
            "WHERE v.startDateTime >= :startDay " +
            "AND v.endDateTime <= :endDay " +
            "AND v.patient IS NOT NULL")
    List<Visit> findByDate(LocalDateTime startDay, LocalDateTime endDay);

    @Query("SELECT v FROM Visit v " +
            "WHERE v.patient.id = :id")
    List<Visit> findByPatientId(Long id);
}