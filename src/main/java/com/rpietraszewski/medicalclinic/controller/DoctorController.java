package com.rpietraszewski.medicalclinic.controller;

import com.rpietraszewski.medicalclinic.model.dto.AssignInstitutionCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.DoctorCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.DoctorDTO;
import com.rpietraszewski.medicalclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping
    public List<DoctorDTO> getDoctors() {
        return doctorService.getDoctors();
    }

    @GetMapping("/{email}")
    public DoctorDTO getDoctor(@PathVariable("email") String email) {
        return doctorService.getDoctor(email);
    }

    @PostMapping
    public DoctorDTO createDoctor(@RequestBody DoctorCreateUpdateDTO doctorCreateUpdateDTO) {
        return doctorService.createDoctor(doctorCreateUpdateDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{email}")
    public void deleteDoctor(@PathVariable("email") String email) {
        doctorService.deleteDoctor(email);
    }

    @PatchMapping("/{email}")
    public DoctorDTO assignInstitutionToDoctor(@PathVariable("email") String email, @RequestBody AssignInstitutionCommandDTO assignInstitutionCommandDTO) {
        return doctorService.assignInstitutionToDoctor(email, assignInstitutionCommandDTO);
    }
}
