package com.rpietraszewski.medicalclinic.service;

import com.rpietraszewski.medicalclinic.exception.PatientEmailAlreadyExistsException;
import com.rpietraszewski.medicalclinic.exception.PatientNotFoundException;
import com.rpietraszewski.medicalclinic.exception.PatientNullFieldsException;
import com.rpietraszewski.medicalclinic.mapper.PatientMapper;
import com.rpietraszewski.medicalclinic.model.dto.ChangePasswordCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientCreateDTO;
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
                .map(patientMapper::patientToPatientDTO)
                .toList();
    }

    public PatientDTO getPatient(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found for email " + email));
        return patientMapper.patientToPatientDTO(patient);
    }

    public PatientDTO createPatient(PatientCreateDTO patientCreateDTO) {
        if (patientRepository.existsByEmail(patientCreateDTO.getEmail())) {
            throw new PatientEmailAlreadyExistsException("Email already exists for email " + patientCreateDTO.getEmail());
        }

        Patient newPatient = patientMapper.patientCreateDTOToPatient(patientCreateDTO);
        return patientMapper.patientToPatientDTO(patientRepository.save(newPatient));
    }

    public void deletePatient(String email) {
        Patient existingPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found for email " + email));
        patientRepository.delete(existingPatient);
    }

    public PatientDTO updatePatient(String email, PatientCreateDTO newPatientCreateDTO) {
        Patient existingPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found for email " + email));

        PatientValidator.validatePatient(existingPatient, newPatientCreateDTO);
        isEmailAvailable(existingPatient.getEmail(), newPatientCreateDTO.getEmail());

        existingPatient.update(patientMapper.patientCreateDTOToPatient(newPatientCreateDTO));

        return patientMapper.patientToPatientDTO(patientRepository.save(existingPatient));
    }

    public PatientDTO updatePassword(String email, ChangePasswordCommandDTO newPassword) {
        Patient existingPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found for email " + email));

        PatientValidator.validatePasswordChange(existingPatient, newPassword);
        if (newPassword == null || newPassword.getNewPassword() == null) {
            throw new PatientNullFieldsException("Provided new password cannot be empty");
        }
        existingPatient.setPassword(newPassword.getNewPassword());
        return patientMapper.patientToPatientDTO(patientRepository.save(existingPatient));
    }

    private void isEmailAvailable(String currentEmail, String newEmail) {
        if (!newEmail.equals(currentEmail)) {
            if (patientRepository.existsByEmail(currentEmail)) {
                throw new PatientEmailAlreadyExistsException("Email already exists for email " + newEmail);
            }
        }
    }
}
