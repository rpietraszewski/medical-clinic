package com.rpietraszewski.medicalclinic.service;

import com.rpietraszewski.medicalclinic.exception.*;
import com.rpietraszewski.medicalclinic.mapper.VisitMapper;
import com.rpietraszewski.medicalclinic.model.dto.AssignPatientCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.FindVisitCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.VisitCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.VisitDTO;
import com.rpietraszewski.medicalclinic.model.entity.Doctor;
import com.rpietraszewski.medicalclinic.model.entity.Institution;
import com.rpietraszewski.medicalclinic.model.entity.Patient;
import com.rpietraszewski.medicalclinic.model.entity.Visit;
import com.rpietraszewski.medicalclinic.repository.DoctorRepository;
import com.rpietraszewski.medicalclinic.repository.InstitutionRepository;
import com.rpietraszewski.medicalclinic.repository.PatientRepository;
import com.rpietraszewski.medicalclinic.repository.VisitRepository;
import com.rpietraszewski.medicalclinic.validator.VisitValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final InstitutionRepository institutionRepository;
    private final DoctorRepository doctorRepository;
    private final VisitMapper visitMapper;
    private final PatientRepository patientRepository;

    public List<VisitDTO> getVisitsAvailableByDoctorAndStartTimeAndEndTime(FindVisitCommandDTO findVisitCommandDTO) {

        Doctor doctor = doctorRepository.findByEmail(findVisitCommandDTO.getDoctorEmail())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found for email " + findVisitCommandDTO.getDoctorEmail()));

        return visitRepository.findByAvailableDoctorAndStartTimeAndEndTime(
                        doctor.getId(),
                        findVisitCommandDTO.getStartTime(),
                        findVisitCommandDTO.getEndTime(),
                        findVisitCommandDTO.getInstitution()).stream()
                .map(visitMapper::toVisitDTO)
                .toList();
    }

    public VisitDTO createVisit(VisitCreateDTO visitCreateDTO) {
        VisitValidator.validateVisit(visitCreateDTO);

        List<Visit> existingVisits = visitRepository.existsByDoctorAndStartTimeAndEndTime(
                visitCreateDTO.getDoctorEmail(),
                visitCreateDTO.getStartDateTime(),
                visitCreateDTO.getEndDateTime()
        );

        if(!existingVisits.isEmpty()){
            throw new VisitAlreadyExistsException("Visit in this time and for this doctor already exists");
        }

        Doctor doctor = doctorRepository.findByEmail(visitCreateDTO.getDoctorEmail())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found for email " + visitCreateDTO.getDoctorEmail()));

        Institution institution = institutionRepository.findById(visitCreateDTO.getInstitution())
                .orElseThrow(() -> new InstitutionNotFoundException("Institution not found for id " + visitCreateDTO.getInstitution()));

        Visit visit = visitMapper.toVisit(visitCreateDTO);
        visit.setDoctor(doctor);
        visit.setInstitution(institution);
        return visitMapper.toVisitDTO(visitRepository.save(visit));
    }

    public VisitDTO assignPatientToVisit(Long visitId, AssignPatientCommandDTO assignPatientCommandDTO) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitNotFoundException("Not found visit for given id " + visitId));

        if (visit.getStartDateTime().isBefore(LocalDateTime.now()) || visit.getEndDateTime().isBefore(LocalDateTime.now())) {
            throw new VisitPastDateException("Chosen visit has expired");
        }

        if (visit.getPatient() != null && visit.getPatient().getId() != null) {
            throw new VisitAlreadyAssignedException("Someone has already assigned to this visit");
        }

        Patient patient = patientRepository.findByEmail(assignPatientCommandDTO.getPatientEmail())
                .orElseThrow(() -> new PatientNotFoundException("Not found patient for email " + assignPatientCommandDTO.getPatientEmail()));

        visit.setPatient(patient);
        return visitMapper.toVisitDTO(visitRepository.save(visit));
    }
}