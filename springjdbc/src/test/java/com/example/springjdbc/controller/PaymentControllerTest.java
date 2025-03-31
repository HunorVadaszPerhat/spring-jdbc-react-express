package com.example.springjdbc.controller;

import com.example.springjdbc.controller.payment.PaymentController;
import com.example.springjdbc.dto.payment.PaymentDTO;
import com.example.springjdbc.service.payment.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService service;

    private PaymentDTO dto;

    @BeforeEach
    void setUp() {
        dto = new PaymentDTO(103, "CHK123", LocalDate.now(), new BigDecimal("250.00"));
    }

    @Test
    void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].checkNumber").value("CHK123"));
    }

    @Test
    void testGetById() throws Exception {
        when(service.getById(103, "CHK123")).thenReturn(dto);

        mockMvc.perform(get("/api/payments/103/CHK123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(250.00));
    }

    @Test
    void testCreate() throws Exception {
        String json = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        String json = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/payments/103/CHK123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/payments/103/CHK123"))
                .andExpect(status().isNoContent());
    }
}
