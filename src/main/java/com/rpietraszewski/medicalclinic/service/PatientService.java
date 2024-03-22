package com.rpietraszewski.medicalclinic.service;

import com.rpietraszewski.medicalclinic.MedicalApplicationHelper;
import com.rpietraszewski.medicalclinic.exception.DateNullException;
import com.rpietraszewski.medicalclinic.exception.PatientEmailAlreadyExistsException;
import com.rpietraszewski.medicalclinic.exception.PatientNotFoundException;
import com.rpietraszewski.medicalclinic.exception.PatientNullFieldsException;
import com.rpietraszewski.medicalclinic.mapper.PatientMapper;
import com.rpietraszewski.medicalclinic.model.dto.ChangePasswordCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientDTO;
import com.rpietraszewski.medicalclinic.model.entity.Patient;
import com.rpietraszewski.medicalclinic.model.entity.Visit;
import com.rpietraszewski.medicalclinic.repository.PatientRepository;
import com.rpietraszewski.medicalclinic.repository.VisitRepository;
import com.rpietraszewski.medicalclinic.validator.PatientValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final VisitRepository visitRepository;

    public List<PatientDTO> getPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toPatientDTO)
                .toList();
    }

    public PatientDTO getPatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found for id " + id));
        return patientMapper.toPatientDTO(patient);
    }

    @Transactional
    public PatientDTO createPatient(PatientCreateUpdateDTO patientCreateUpdateDTO) {
        if (MedicalApplicationHelper.patientHasNullFields(patientCreateUpdateDTO)) {
            throw new PatientNullFieldsException("Patient cannot have empty fields");
        }

        if (patientRepository.existsByEmail(patientCreateUpdateDTO.getEmail())) {
            throw new PatientEmailAlreadyExistsException("Email already exists for email " + patientCreateUpdateDTO.getEmail());
        }

        Patient newPatient = patientMapper.toPatient(patientCreateUpdateDTO);
        return patientMapper.toPatientDTO(patientRepository.save(newPatient));
    }

    public void deletePatient(Long id) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found for id " + id));
        patientRepository.delete(existingPatient);
    }

    @Transactional
    public PatientDTO updatePatient(Long id, PatientCreateUpdateDTO newPatientCreateUpdateDTO) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found for id " + id));

        PatientValidator.validatePatient(existingPatient, newPatientCreateUpdateDTO);
        isEmailChangeAvailable(existingPatient.getEmail(), newPatientCreateUpdateDTO.getEmail());

        existingPatient.update(patientMapper.toPatient(newPatientCreateUpdateDTO));

        return patientMapper.toPatientDTO(patientRepository.save(existingPatient));
    }

    @Transactional
    public PatientDTO updatePassword(Long id, ChangePasswordCommandDTO newPassword) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found for id " + id));

        PatientValidator.validatePasswordChange(existingPatient, newPassword);

        existingPatient.setPassword(newPassword.getNewPassword());
        return patientMapper.toPatientDTO(patientRepository.save(existingPatient));
    }

    public List<PatientDTO> getPatientWithVisitFromDate(LocalDate date) {
        if (date == null) {
            throw new DateNullException("Date can't be empty");
        }

        LocalDateTime startDay = date.atStartOfDay();
        LocalDateTime endDay = date.atTime(LocalTime.MAX);

        List<Visit> visits = visitRepository.findByDate(startDay, endDay);

        Set<Long> uniquePatientIds = visits.stream()
                .map(v -> v.getPatient().getId())
                .collect(Collectors.toSet());

        return uniquePatientIds.stream()
                .map(this::getPatient)
                .toList();
    }

    private void isEmailChangeAvailable(String currentEmail, String newEmail) {
        if (!newEmail.equals(currentEmail)) {
            if (patientRepository.existsByEmail(currentEmail)) {
                throw new PatientEmailAlreadyExistsException("Email already exists for email " + newEmail);
            }
        }
    }
}
