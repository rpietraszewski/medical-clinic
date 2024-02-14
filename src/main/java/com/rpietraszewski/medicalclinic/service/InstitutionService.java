package com.rpietraszewski.medicalclinic.service;

import com.rpietraszewski.medicalclinic.exception.InstitutionNameAlreadyExistsException;
import com.rpietraszewski.medicalclinic.exception.InstitutionNotFoundException;
import com.rpietraszewski.medicalclinic.mapper.InstitutionMapper;
import com.rpietraszewski.medicalclinic.model.dto.InstitutionCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.InstitutionDTO;
import com.rpietraszewski.medicalclinic.model.entity.Institution;
import com.rpietraszewski.medicalclinic.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitutionService {
    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    public List<InstitutionDTO> getInstitutions() {
        return institutionRepository.findAll().stream()
                .map(institutionMapper::toInstitutionDTO)
                .toList();
    }

    public InstitutionDTO getInstitution(Long id) {
        Institution institution = institutionRepository.findById(id)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution not found for id " + id));

        return institutionMapper.toInstitutionDTO(institution);
    }

    public InstitutionDTO createInstitution(InstitutionCreateDTO institutionCreateDTO) {
        if (institutionRepository.existsByName(institutionCreateDTO.getName())) {
            throw new InstitutionNameAlreadyExistsException("Name already exists for name " + institutionCreateDTO.getName());
        }

        Institution newInstitution = institutionMapper.toInstitution(institutionCreateDTO);
        return institutionMapper.toInstitutionDTO(institutionRepository.save(newInstitution));
    }

    public void deleteInstitution(String name) {
        Institution existingInstitution = institutionRepository.findByName(name)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution not found for name " + name));
        institutionRepository.delete(existingInstitution);
    }
}
