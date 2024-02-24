package com.rpietraszewski.medicalclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpietraszewski.medicalclinic.TestDataFactory;
import com.rpietraszewski.medicalclinic.model.dto.InstitutionCreateDTO;
import com.rpietraszewski.medicalclinic.model.dto.InstitutionDTO;
import com.rpietraszewski.medicalclinic.service.InstitutionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InstitutionControllerTest {
    @MockBean
    InstitutionService institutionService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getInstitutions_DataCorrect_ListInstitutionDtoReturned() throws Exception {
        //given
        InstitutionDTO institutionDTO1 = TestDataFactory.createInstitutionDTO();

        InstitutionDTO institutionDTO2 = TestDataFactory.createInstitutionDTO();

        when(institutionService.getInstitutions()).thenReturn(List.of(institutionDTO1, institutionDTO2));

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.get("/institutions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(institutionDTO1.getId()))
                .andExpect(jsonPath("$[0].name").value(institutionDTO1.getName()))
                .andExpect(jsonPath("$[1].id").value(institutionDTO2.getId()))
                .andExpect(jsonPath("$[1].name").value(institutionDTO2.getName()));
    }

    @Test
    void getInstitution_DataCorrect_InstitutionDtoReturned() throws Exception {
        //given
        InstitutionDTO institutionDTO = TestDataFactory.createInstitutionDTO();

        when(institutionService.getInstitution(any())).thenReturn(institutionDTO);

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.get("/institutions/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(institutionDTO.getId()))
                .andExpect(jsonPath("$.name").value(institutionDTO.getName()));
    }

    @Test
    void createInstitution_DataCorrect_InstitutionDtoReturned() throws Exception {
        //given
        InstitutionCreateDTO institutionCreateDTO = TestDataFactory.createInstitutionCreateDTO();

        InstitutionDTO institutionDTO = TestDataFactory.createInstitutionDTO();

        when(institutionService.createInstitution(any())).thenReturn(institutionDTO);

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.post("/institutions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(institutionCreateDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(institutionDTO.getId()))
                .andExpect(jsonPath("$.name").value(institutionDTO.getName()));
    }

    @Test
    void deleteInstitution_DataCorrect_InstitutionDeleted() throws Exception {
        //given
        doNothing().when(institutionService).deleteInstitution(any());

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.delete("/institutions/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
