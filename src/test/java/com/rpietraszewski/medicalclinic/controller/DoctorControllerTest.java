package com.rpietraszewski.medicalclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpietraszewski.medicalclinic.TestDataFactory;
import com.rpietraszewski.medicalclinic.model.dto.AssignInstitutionCommandDTO;
import com.rpietraszewski.medicalclinic.model.dto.DoctorCreateUpdateDTO;
import com.rpietraszewski.medicalclinic.model.dto.DoctorDTO;
import com.rpietraszewski.medicalclinic.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTest {
    @MockBean
    DoctorService doctorService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getDoctors_DataCorrect_ListDoctorDtoReturned() throws Exception {
        //given
        DoctorDTO doctorDTO1 = TestDataFactory.createDoctorDTO("doctor1@doctor.pl");

        DoctorDTO doctorDTO2 = TestDataFactory.createDoctorDTO("doctor2@doctor.pl");

        when(doctorService.getDoctors()).thenReturn(List.of(doctorDTO1, doctorDTO2));

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.get("/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(doctorDTO1.getEmail()))
                .andExpect(jsonPath("$[1].email").value(doctorDTO2.getEmail()));
    }

    @Test
    void getDoctor_DataCorrect_DoctorDtoReturned() throws Exception {
        //given
        DoctorDTO doctorDTO = TestDataFactory.createDoctorDTO("doctor@doctor.pl");

        when(doctorService.getDoctor(any())).thenReturn(doctorDTO);

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/{email}", "doctor@doctor.pl")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(doctorDTO.getEmail()));
    }

    @Test
    void createDoctor_DataCorrect_DoctorDtoReturned() throws Exception {
        //given
        DoctorCreateUpdateDTO doctorCreateUpdateDTO = TestDataFactory
                .createDoctorCreateUpdateDTO("doctor@doctor.pl");

        DoctorDTO doctorDTO = TestDataFactory.createDoctorDTO("doctor@doctor.pl");

        when(doctorService.createDoctor(any())).thenReturn(doctorDTO);

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorCreateUpdateDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(doctorDTO.getEmail()));
    }

    @Test
    void deleteDoctor_DataCorrect_DoctorDeleted() throws Exception {
        //given
        doNothing().when(doctorService).deleteDoctor(any());

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.delete("/doctors/{email}", "doctor@doctor.pl")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(doctorService).deleteDoctor(any());
    }

    @Test
    void assignInstitutionToDoctor_DataCorrect_DoctorDtoReturned() throws Exception {
        //given
        DoctorDTO doctorDTO = TestDataFactory.createDoctorDTO("doctor@doctor.pl");
        doctorDTO.setInstitutions(Set.of(1L));

        AssignInstitutionCommandDTO assignInstitutionCommandDTO = AssignInstitutionCommandDTO.builder()
                .id(1L)
                .build();

        when(doctorService.assignInstitutionToDoctor(any(), any())).thenReturn(doctorDTO);

        //when && then
        mockMvc.perform(MockMvcRequestBuilders.patch("/doctors/{email}", "doctor@doctor.pl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignInstitutionCommandDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(doctorDTO.getEmail()))
                .andExpect(jsonPath("$.institutions").value(1));
    }
}
