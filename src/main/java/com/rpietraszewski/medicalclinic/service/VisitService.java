package com.rpietraszewski.medicalclinic.service;

import com.rpietraszewski.medicalclinic.exception.DoctorNotFoundException;
import com.rpietraszewski.medicalclinic.exception.InstitutionNotFoundException;
import com.rpietraszewski.medicalclinic.exception.VisitAlreadyExistsException;
import com.rpietraszewski.medicalclinic.mapper.VisitMapper;
import com.rpietraszewski.medicalclinic.model.dto.FindVisitCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.VisitCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.VisitDTO;
import com.rpietraszewski.medicalclinic.model.entity.Doctor;
import com.rpietraszewski.medicalclinic.model.entity.Institution;
import com.rpietraszewski.medicalclinic.model.entity.Visit;
import com.rpietraszewski.medicalclinic.repository.DoctorRepository;
import com.rpietraszewski.medicalclinic.repository.InstitutionRepository;
import com.rpietraszewski.medicalclinic.repository.VisitRepository;
import com.rpietraszewski.medicalclinic.validator.VisitValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final InstitutionRepository institutionRepository;
    private final DoctorRepository doctorRepository;
    private final VisitMapper visitMapper;

    public List<VisitDTO> getVisitsByDoctorAndStartTimeAndEndTime(FindVisitCommandDTO findVisitCommandDTO){
        Doctor doctor = doctorRepository.findByEmail(findVisitCommandDTO.getDoctorEmail())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found for email " + findVisitCommandDTO.getDoctorEmail()));

        return visitRepository.findByDoctorAndStartTimeAndEndTime(doctor,
                        findVisitCommandDTO.getStartTime(),
                        findVisitCommandDTO.getEndTime()).stream()
                .map(visitMapper::toVisitDTO)
                .toList();
    }

    public VisitDTO createVisit(VisitCreateDTO visitCreateDTO){
        VisitValidator.validateVisit(visitCreateDTO);

        Visit visit = visitMapper.toVisit(visitCreateDTO);
        if(visitRepository.exists(Example.of(visit))){
            throw new VisitAlreadyExistsException("Visit with this values already exists");
        }

        Doctor doctor = doctorRepository.findByEmail(visitCreateDTO.getDoctorEmail())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found for email " + visitCreateDTO.getDoctorEmail()));

        Institution institution = institutionRepository.findById(visitCreateDTO.getInstitution())
                .orElseThrow(() -> new InstitutionNotFoundException("Institution not found for id " + visitCreateDTO.getInstitution()));

        visit.setDoctor(doctor);
        visit.setInstitution(institution);
        return visitMapper.toVisitDTO(visitRepository.save(visit));
    }
}