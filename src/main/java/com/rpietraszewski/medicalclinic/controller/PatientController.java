package com.rpietraszewski.medicalclinic.controller;

import com.rpietraszewski.medicalclinic.model.dto.ChangePasswordCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientDTO;
import com.rpietraszewski.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientDTO> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping("/{email}")
    public PatientDTO getPatient(@PathVariable String email) {
        return patientService.getPatient(email);
    }

    @PostMapping
    public PatientDTO createPatient(@RequestBody PatientCreateDTO patientDTO) {
        return patientService.createPatient(patientDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{email}")
    public void deletePatient(@PathVariable String email) {
        patientService.deletePatient(email);
    }

    @PutMapping("/{email}")
    public PatientDTO updatePatient(@PathVariable String email, @RequestBody PatientCreateDTO patientCreateDTO) {
        return patientService.updatePatient(email, patientCreateDTO);
    }

    @PatchMapping("/{email}")
    public PatientDTO updatePassword(@PathVariable String email, @RequestBody ChangePasswordCommandDTO changePasswordCommandDTO) {
        return patientService.updatePassword(email, changePasswordCommandDTO);
    }
}
