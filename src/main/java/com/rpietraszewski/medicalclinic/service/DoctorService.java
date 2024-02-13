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
                .map(doctorMapper::doctorToDoctorDTO)
                .toList();
    }

    public DoctorDTO getDoctor(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found for email " + email));

        return doctorMapper.doctorToDoctorDTO(doctor);
    }

    public DoctorDTO createDoctor(DoctorCreateUpdateDTO doctorCreateUpdateDTO) {
        if (doctorRepository.existsByEmail(doctorCreateUpdateDTO.getEmail())) {
            throw new DoctorEmailAlreadyExistsException("Email already exists for email " + doctorCreateUpdateDTO.getEmail());
        }

        Doctor newDoctor = doctorMapper.doctorCreateUpdateDTOToDoctor(doctorCreateUpdateDTO);
        return doctorMapper.doctorToDoctorDTO(doctorRepository.save(newDoctor));
    }

    public void deleteDoctor(String email) {
        Doctor existingDoctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found for email" + email));
        doctorRepository.delete(existingDoctor);
    }

    public DoctorDTO assignInstitutionToDoctor(String email, AssignInstitutionCommandDTO name) {
        Doctor existingDoctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found for email" + email));

        DoctorValidator.validateNullAssignInstitution(name);
        Institution existingInstitution = institutionRepository.findByName(name.getName())
                .orElseThrow(() -> new InstitutionNotFoundException("Institution not found for name " + name));

        DoctorValidator.validateAssignInstitutionExists(existingDoctor, existingInstitution);

        existingDoctor.getInstitutions().add(existingInstitution);

        return doctorMapper.doctorToDoctorDTO(doctorRepository.save(existingDoctor));
    }
}
