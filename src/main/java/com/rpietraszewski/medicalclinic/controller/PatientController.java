package com.rpietraszewski.medicalclinic.controller;

import com.rpietraszewski.medicalclinic.model.dto.ChangePasswordCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientCreateUpdateDTO;
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
    public PatientDTO getPatient(@PathVariable("email") String email) {
        return patientService.getPatient(email);
    }

    @PostMapping
    public PatientDTO createPatient(@RequestBody PatientCreateUpdateDTO patientDTO) {
        return patientService.createPatient(patientDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{email}")
    public void deletePatient(@PathVariable("email") String email) {
        patientService.deletePatient(email);
    }

    @PutMapping("/{email}")
    public PatientDTO updatePatient(@PathVariable("email") String email, @RequestBody PatientCreateUpdateDTO patientDTO) {
        return patientService.updatePatient(email, patientDTO);
    }

    @PatchMapping("/{email}")
    public PatientDTO updatePassword(@PathVariable("email") String email, @RequestBody ChangePasswordCommandDTO changePasswordCommandDTO) {
        return patientService.updatePassword(email, changePasswordCommandDTO);
    }
}
