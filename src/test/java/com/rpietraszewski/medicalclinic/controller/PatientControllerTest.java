package com.rpietraszewski.medicalclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpietraszewski.medicalclinic.TestDataFactory;
import com.rpietraszewski.medicalclinic.model.dto.ChangePasswordCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.PatientDTO;
import com.rpietraszewski.medicalclinic.service.PatientService;
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
public class PatientControllerTest {
    @MockBean
    PatientService patientService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getPatient_DataCorrect_ListPatientDtoReturned() throws Exception {
        //given
        PatientDTO patientDTO1 = TestDataFactory.createPatientDTO("patient1@patient.pl");

        PatientDTO patientDTO2 = TestDataFactory.createPatientDTO("patient2@patient.pl");

        when(patientService.getPatients()).thenReturn(List.of(patientDTO1, patientDTO2));

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.get("/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(patientDTO1.getEmail()))
                .andExpect(jsonPath("$[1].email").value(patientDTO2.getEmail()));
    }

    @Test
    void getPatient_DataCorrect_PatientDtoReturned() throws Exception {
        //given
        PatientDTO patientDTO = TestDataFactory.createPatientDTO("patient@patient.pl");

        when(patientService.getPatient(any())).thenReturn(patientDTO);

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(patientDTO.getId()));
    }

    @Test
    void createPatient_DataCorrect_PatientDtoReturned() throws Exception {
        //given
        PatientCreateUpdateDTO patientCreateUpdateDTO = TestDataFactory
                .createPatientCreateUpdateDTO("patient@patient.pl", "idCardNo");

        PatientDTO patientDTO = TestDataFactory.createPatientDTO("patient@patient.pl");

        when(patientService.createPatient(any())).thenReturn(patientDTO);

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientCreateUpdateDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(patientDTO.getEmail()));
    }

    @Test
    void deletePatient_DataCorrect_PatientDeleted() throws Exception {
        //given
        doNothing().when(patientService).deletePatient(any());

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void updatePatient_DataCorrect_PatientDtoReturned() throws Exception {
        //given
        PatientCreateUpdateDTO patientCreateUpdateDTO = TestDataFactory
                .createPatientCreateUpdateDTO("patient@patient.pl", "idCardNo");

        PatientDTO patientDTO = TestDataFactory.createPatientDTO("patient@patient.pl");

        when(patientService.updatePatient(any(), any())).thenReturn(patientDTO);

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.put("/patients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientCreateUpdateDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(patientDTO.getId()));
    }

    @Test
    void updatePassword_DataCorrect_PatientDtoReturned() throws Exception {
        //given
        PatientDTO patientDTO = TestDataFactory.createPatientDTO("patient@patient.pl");

        ChangePasswordCommandDTO changePasswordCommandDTO = ChangePasswordCommandDTO.builder()
                .newPassword("newPassword")
                .build();

        when(patientService.updatePassword(any(), any())).thenReturn(patientDTO);

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.patch("/patients/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changePasswordCommandDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(patientDTO.getId()));
    }
}
