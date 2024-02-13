package com.rpietraszewski.medicalclinic.service;

import com.rpietraszewski.medicalclinic.exception.InstitutionNameAlreadyExistsException;
import com.rpietraszewski.medicalclinic.exception.InstitutionNotFoundException;
import com.rpietraszewski.medicalclinic.mapper.InstitutionMapper;
import com.rpietraszewski.medicalclinic.model.dto.InstitutionCRUDDTO;
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

    public List<InstitutionCRUDDTO> getInstitutions() {
        return institutionRepository.findAll().stream()
                .map(institutionMapper::institutionToInstitutionCRUDDTO)
                .toList();
    }

    public InstitutionCRUDDTO getInstitution(String name) {
        Institution institution = institutionRepository.findByName(name)
                .orElseThrow(() -> new InstitutionNameAlreadyExistsException("Institution not found for name " + name));

        return institutionMapper.institutionToInstitutionCRUDDTO(institution);
    }

    public InstitutionCRUDDTO createInstitution(InstitutionCRUDDTO institutionCRUDDTO) {
        if (institutionRepository.existsByName(institutionCRUDDTO.getName())) {
            throw new InstitutionNameAlreadyExistsException("Name already exists for name " + institutionCRUDDTO.getName());
        }

        Institution newInstitution = institutionMapper.institutionCRUDDTOToInstitution(institutionCRUDDTO);
        return institutionMapper.institutionToInstitutionCRUDDTO(institutionRepository.save(newInstitution));
    }

    public void deleteInstitution(String name) {
        Institution existingInstitution = institutionRepository.findByName(name)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution not found for name " + name));
        institutionRepository.delete(existingInstitution);
    }
}
