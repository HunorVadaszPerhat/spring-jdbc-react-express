package com.example.springjdbc.controller;

import com.example.springjdbc.controller.orderdetail.OrderDetailController;
import com.example.springjdbc.dto.orderdetail.OrderDetailDTO;
import com.example.springjdbc.service.orderdetail.OrderDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderDetailController.class)
public class OrderDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderDetailService service;

    private OrderDetailDTO dto;

    @BeforeEach
    void setUp() {
        dto = new OrderDetailDTO(10100, "S10_1678", 30, new BigDecimal("100.00"), 1);
    }

    @Test
    void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/order-details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productCode").value("S10_1678"));
    }

    @Test
    void testGetById() throws Exception {
        when(service.getById(10100, "S10_1678")).thenReturn(dto);

        mockMvc.perform(get("/api/order-details/10100/S10_1678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantityOrdered").value(30));
    }

    @Test
    void testCreate() throws Exception {
        String json = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/api/order-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        String json = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/order-details/10100/S10_1678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/order-details/10100/S10_1678"))
                .andExpect(status().isNoContent());
    }
}
