package com.rpietraszewski.medicalclinic.controller;

import com.rpietraszewski.medicalclinic.model.dto.FindVisitCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.VisitCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.VisitDTO;
import com.rpietraszewski.medicalclinic.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;

    @GetMapping
    public List<VisitDTO> getVisits(@RequestBody FindVisitCommandDTO findVisitCommandDTO) {
        return visitService.getVisitsAvailableByDoctorAndStartTimeAndEndTime(findVisitCommandDTO);
    }

    @PostMapping
    public VisitDTO createVisit(@RequestBody VisitCreateDTO visitCreateDTO) {
        return visitService.createVisit(visitCreateDTO);
    }
}
