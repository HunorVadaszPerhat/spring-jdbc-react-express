package com.example.springjdbc.controller;

import com.example.springjdbc.controller.office.OfficeController;
import com.example.springjdbc.dto.office.OfficeDTO;
import com.example.springjdbc.service.office.OfficeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OfficeController.class)
public class OfficeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfficeService service;

    private OfficeDTO dto;

    @BeforeEach
    void setUp() {
        dto = new OfficeDTO("1", "Copenhagen", "123456", "Main St", null, null, "Denmark", "2100", "EU");
    }

    @Test
    void testGetAllOffices() throws Exception {
        when(service.getAllOffices()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/offices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Copenhagen"));
    }

    @Test
    void testGetOfficeById() throws Exception {
        when(service.getOfficeById("1")).thenReturn(dto);

        mockMvc.perform(get("/api/offices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Copenhagen"));
    }

    @Test
    void testCreateOffice() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/offices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateOffice() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dto);

        mockMvc.perform(put("/api/offices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteOffice() throws Exception {
        mockMvc.perform(delete("/api/offices/1"))
                .andExpect(status().isNoContent());
    }
}
