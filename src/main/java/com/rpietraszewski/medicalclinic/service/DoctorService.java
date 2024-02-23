package com.rpietraszewski.medicalclinic.service;

import com.rpietraszewski.medicalclinic.exception.DoctorEmailAlreadyExistsException;
import com.rpietraszewski.medicalclinic.exception.DoctorNotFoundException;
import com.rpietraszewski.medicalclinic.exception.InstitutionNotFoundException;
import com.rpietraszewski.medicalclinic.mapper.DoctorMapper;
import com.rpietraszewski.medicalclinic.model.dto.AssignInstitutionCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.DoctorCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.DoctorDTO;
import com.rpietraszewski.medicalclinic.model.entity.Doctor;
import com.rpietraszewski.medicalclinic.model.entity.Institution;
import com.rpietraszewski.medicalclinic.repository.DoctorRepository;
import com.rpietraszewski.medicalclinic.repository.InstitutionRepository;
import com.rpietraszewski.medicalclinic.validator.DoctorValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final InstitutionRepository institutionRepository;
    private final DoctorMapper doctorMapper;

    public List<DoctorDTO> getDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDoctorDTO)
                .toList();
    }

    public DoctorDTO getDoctor(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found for email " + email));

        return doctorMapper.toDoctorDTO(doctor);
    }

    @Transactional
    public DoctorDTO createDoctor(DoctorCreateUpdateDTO doctorCreateUpdateDTO) {
        if (doctorRepository.existsByEmail(doctorCreateUpdateDTO.getEmail())) {
            throw new DoctorEmailAlreadyExistsException("Email already exists for email " + doctorCreateUpdateDTO.getEmail());
        }

        Doctor newDoctor = doctorMapper.toDoctor(doctorCreateUpdateDTO);
        return doctorMapper.toDoctorDTO(doctorRepository.save(newDoctor));
    }

    public void deleteDoctor(String email) {
        Doctor existingDoctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found for email " + email));
        doctorRepository.delete(existingDoctor);
    }

    @Transactional
    public DoctorDTO assignInstitutionToDoctor(String email, AssignInstitutionCommandDTO assignInstitutionCommandDTO) {
        Doctor existingDoctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found for email " + email));

        DoctorValidator.validateNullAssignInstitution(assignInstitutionCommandDTO);
        Institution existingInstitution = institutionRepository.findById(assignInstitutionCommandDTO.getId())
                .orElseThrow(() -> new InstitutionNotFoundException("Institution not found for id " + assignInstitutionCommandDTO.getId()));

        DoctorValidator.validateAssignInstitutionExists(existingDoctor, existingInstitution);

        existingDoctor.getInstitutions().add(existingInstitution);

        return doctorMapper.toDoctorDTO(doctorRepository.save(existingDoctor));
    }
}
