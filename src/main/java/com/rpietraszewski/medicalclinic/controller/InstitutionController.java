package com.rpietraszewski.medicalclinic.controller;

import com.rpietraszewski.medicalclinic.model.dto.InstitutionCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.InstitutionDTO;
import com.rpietraszewski.medicalclinic.service.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/institutions")
@RequiredArgsConstructor
public class InstitutionController {
    private final InstitutionService institutionService;

    @GetMapping
    public List<InstitutionDTO> getInstitutions() {
        return institutionService.getInstitutions();
    }

    @GetMapping("/{id}")
    public InstitutionDTO getInstitution(@PathVariable("id") Long id) {
        return institutionService.getInstitution(id);
    }

    @PostMapping
    public InstitutionDTO createInstitution(@RequestBody InstitutionCreateDTO institutionCreateDTO) {
        return institutionService.createInstitution(institutionCreateDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteInstitution(@PathVariable("id") Long id) {
        institutionService.deleteInstitution(id);
    }
}
