package com.rpietraszewski.medicalclinic.service;

import com.rpietraszewski.medicalclinic.TestDataFactory;
import com.rpietraszewski.medicalclinic.exception.InstitutionNameAlreadyExistsException;
import com.rpietraszewski.medicalclinic.exception.InstitutionNotFoundException;
import com.rpietraszewski.medicalclinic.mapper.InstitutionMapper;
import com.rpietraszewski.medicalclinic.model.dto.InstitutionCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.InstitutionDTO;
import com.rpietraszewski.medicalclinic.model.entity.Institution;
import com.rpietraszewski.medicalclinic.repository.InstitutionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InstitutionServiceTest {
    InstitutionRepository institutionRepository;
    InstitutionMapper institutionMapper;
    InstitutionService institutionService;

    @BeforeEach
    void setup(){
        this.institutionRepository = Mockito.mock(InstitutionRepository.class);
        this.institutionMapper = Mappers.getMapper(InstitutionMapper.class);
        this.institutionService = new InstitutionService(institutionRepository, institutionMapper);
    }

    @Test
    void getInstitutions_DataCorrect_ListInstitutionDtoReturner(){
        Institution institution1 = Institution.builder()
                .id(1L)
                .name("name1")
                .city("city1")
                .zipCode("zipCode1")
                .street("street1")
                .buildingNumber("number1")
                .doctors(new HashSet<>())
                .build();

        Institution institution2 = Institution.builder()
                .id(2L)
                .name("name2")
                .city("city2")
                .zipCode("zipCode2")
                .street("street2")
                .buildingNumber("number2")
                .doctors(new HashSet<>())
                .build();

        when(institutionRepository.findAll()).thenReturn(List.of(institution1, institution2));

        List<InstitutionDTO> result = institutionService.getInstitutions();

        assertEquals(2, result.size());

        assertEquals(1L, result.get(0).getId());
        assertEquals("city1", result.get(0).getCity());
        assertEquals("zipCode1", result.get(0).getZipCode());
        assertEquals("street1", result.get(0).getStreet());
        assertEquals("number1", result.get(0).getBuildingNumber());
        assertEquals("name1", result.get(0).getName());
        assertTrue(result.get(0).getDoctors().isEmpty());

        assertEquals(2L, result.get(1).getId());
        assertEquals("city2", result.get(1).getCity());
        assertEquals("zipCode2", result.get(1).getZipCode());
        assertEquals("street2", result.get(1).getStreet());
        assertEquals("number2", result.get(1).getBuildingNumber());
        assertEquals("name2", result.get(1).getName());
        assertTrue(result.get(1).getDoctors().isEmpty());
    }

    @Test
    void getInstitution_DataCorrect_InstitutionDtoReturned(){
        Institution institution = Institution.builder()
                .id(1L)
                .name("name")
                .city("city")
                .zipCode("zipCode")
                .street("street")
                .buildingNumber("number")
                .doctors(new HashSet<>())
                .build();

        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));

        InstitutionDTO result = institutionService.getInstitution(1L);

        assertEquals(1L, result.getId());
        assertEquals("name", result.getName());
        assertEquals("city", result.getCity());
        assertEquals("zipCode", result.getZipCode());
        assertEquals("street", result.getStreet());
        assertEquals("number", result.getBuildingNumber());
        assertTrue(result.getDoctors().isEmpty());
    }

    @Test
    void createInstitution_DataCorrect_InstitutionDtoReturned(){
        Institution institution = Institution.builder()
                .id(1L)
                .name("name")
                .city("city")
                .zipCode("zipCode")
                .street("street")
                .buildingNumber("number")
                .doctors(new HashSet<>())
                .build();

        InstitutionCreateDTO institutionCreateDTO = InstitutionCreateDTO.builder()
                .name("name")
                .city("city")
                .zipCode("zipCode")
                .street("street")
                .buildingNumber("number")
                .build();

        when(institutionRepository.existsByName("name")).thenReturn(false);
        when(institutionRepository.save(any())).thenReturn(institution);

        InstitutionDTO result = institutionService.createInstitution(institutionCreateDTO);

        assertEquals(1L, result.getId());
        assertEquals("name", result.getName());
        assertEquals("city", result.getCity());
        assertEquals("zipCode", result.getZipCode());
        assertEquals("street", result.getStreet());
        assertEquals("number", result.getBuildingNumber());
        assertTrue(result.getDoctors().isEmpty());
    }

    @Test
    void deleteInstitution_DataCorrect_InstitutionDeleted(){
        Institution institution = Institution.builder()
                .id(1L)
                .name("name")
                .city("city")
                .zipCode("zipCode")
                .street("street")
                .buildingNumber("number")
                .doctors(new HashSet<>())
                .build();

        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));

        institutionService.deleteInstitution(1L);

        verify(institutionRepository).delete(institution);
    }

    @Test
    void getInstitution_InstitutionNotFound_InstitutionNotFoundExceptionThrown(){
        //given
        when(institutionRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        InstitutionNotFoundException exception = Assertions.assertThrows(InstitutionNotFoundException.class,
                () -> institutionService.deleteInstitution(1L));

        //then
        assertEquals("Institution not found for id 1", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void createInstitution_InstitutionNameAlreadyExists_InstitutionNameAlreadyExistsExceptionThrown(){
        //given
        InstitutionCreateDTO institutionCreateDTO = TestDataFactory.createInstitutionCreateDTO();

        when(institutionRepository.existsByName(any())).thenReturn(true);

        //when
        InstitutionNameAlreadyExistsException exception = Assertions.assertThrows(InstitutionNameAlreadyExistsException.class,
                () -> institutionService.createInstitution(institutionCreateDTO));

        //then
        assertEquals("Name already exists for name institutionName", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
    }

    @Test
    void deleteInstitution_InstitutionNotFound_InstitutionNotFoundExceptionThrown(){
        //given
        when(institutionRepository.findByName(any())).thenReturn(Optional.empty());

        //when
        InstitutionNotFoundException exception = Assertions.assertThrows(InstitutionNotFoundException.class,
                () -> institutionService.deleteInstitution(1L));

        //then
        assertEquals("Institution not found for id 1", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}
