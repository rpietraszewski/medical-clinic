package com.rpietraszewski.medicalclinic.controller;

import com.rpietraszewski.medicalclinic.model.dto.ChangePasswordCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientDTO;
import com.rpietraszewski.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/{id}")
    public PatientDTO getPatient(@PathVariable("id") Long id) {
        return patientService.getPatient(id);
    }

    @PostMapping
    public PatientDTO createPatient(@RequestBody PatientCreateUpdateDTO patientDTO) {
        return patientService.createPatient(patientDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatient(id);
    }

    @PutMapping("/{id}")
    public PatientDTO updatePatient(@PathVariable("id") Long id, @RequestBody PatientCreateUpdateDTO patientDTO) {
        return patientService.updatePatient(id, patientDTO);
    }

    @PatchMapping("/{id}")
    public PatientDTO updatePassword(@PathVariable("id") Long id, @RequestBody ChangePasswordCommandDTO changePasswordCommandDTO) {
        return patientService.updatePassword(id, changePasswordCommandDTO);
    }

    @GetMapping(params = "date")
    public List<PatientDTO> getPatientsWithVisitFromDate(@RequestParam LocalDate date){
        return patientService.getPatientWithVisitFromDate(date);
    }
}
