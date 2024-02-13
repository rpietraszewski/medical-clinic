package com.rpietraszewski.medicalclinic.controller;

import com.rpietraszewski.medicalclinic.model.dto.InstitutionCRUDDTO;
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
    public List<InstitutionCRUDDTO> getInstitutions() {
        return institutionService.getInstitutions();
    }

    @GetMapping("/{name}")
    public InstitutionCRUDDTO getInstitution(@PathVariable("name") String name) {
        return institutionService.getInstitution(name);
    }

    @PostMapping
    public InstitutionCRUDDTO createInstitution(@RequestBody InstitutionCRUDDTO institutionCRUDDTO) {
        return institutionService.createInstitution(institutionCRUDDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void deleteInstitution(@PathVariable("name") String name) {
        institutionService.deleteInstitution(name);
    }
}
