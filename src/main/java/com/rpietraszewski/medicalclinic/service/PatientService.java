package com.rpietraszewski.medicalclinic.service;

import com.rpietraszewski.medicalclinic.exception.PatientEmailAlreadyExistsException;
import com.rpietraszewski.medicalclinic.exception.PatientNotFoundException;
import com.rpietraszewski.medicalclinic.mapper.PatientMapper;
import com.rpietraszewski.medicalclinic.model.dto.ChangePasswordCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientDTO;
import com.rpietraszewski.medicalclinic.model.entity.Patient;
import com.rpietraszewski.medicalclinic.repository.PatientRepository;
import com.rpietraszewski.medicalclinic.validator.PatientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDTO> getPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toPatientDTO)
                .toList();
    }

    public PatientDTO getPatient(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found for email " + email));
        return patientMapper.toPatientDTO(patient);
    }

    public PatientDTO createPatient(PatientCreateUpdateDTO patientCreateUpdateDTO) {
        if (patientRepository.existsByEmail(patientCreateUpdateDTO.getEmail())) {
            throw new PatientEmailAlreadyExistsException("Email already exists for email " + patientCreateUpdateDTO.getEmail());
        }

        Patient newPatient = patientMapper.toPatient(patientCreateUpdateDTO);
        return patientMapper.toPatientDTO(patientRepository.save(newPatient));
    }

    public void deletePatient(String email) {
        Patient existingPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found for email " + email));
        patientRepository.delete(existingPatient);
    }

    public PatientDTO updatePatient(String email, PatientCreateUpdateDTO newPatientCreateUpdateDTO) {
        Patient existingPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found for email " + email));

        PatientValidator.validatePatient(existingPatient, newPatientCreateUpdateDTO);
        isEmailChangeAvailable(existingPatient.getEmail(), newPatientCreateUpdateDTO.getEmail());

        existingPatient.update(patientMapper.toPatient(newPatientCreateUpdateDTO));

        return patientMapper.toPatientDTO(patientRepository.save(existingPatient));
    }

    public PatientDTO updatePassword(String email, ChangePasswordCommandDTO newPassword) {
        Patient existingPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found for email " + email));

        PatientValidator.validatePasswordChange(existingPatient, newPassword);

        existingPatient.setPassword(newPassword.getNewPassword());
        return patientMapper.toPatientDTO(patientRepository.save(existingPatient));
    }

    private void isEmailChangeAvailable(String currentEmail, String newEmail) {
        if (!newEmail.equals(currentEmail)) {
            if (patientRepository.existsByEmail(currentEmail)) {
                throw new PatientEmailAlreadyExistsException("Email already exists for email " + newEmail);
            }
        }
    }
}
