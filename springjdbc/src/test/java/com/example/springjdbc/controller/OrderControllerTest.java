package com.example.springjdbc.controller;

import com.example.springjdbc.controller.order.OrderController;
import com.example.springjdbc.dto.order.OrderDTO;
import com.example.springjdbc.service.order.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService service;

    private OrderDTO dto;

    @BeforeEach
    void setUp() {
        dto = new OrderDTO(101, LocalDate.now(), LocalDate.now().plusDays(3), null, "Processing", "Handle with care", 1);
    }

    @Test
    void testGetAllOrders() throws Exception {
        when(service.getAllOrders()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("Processing"));
    }

    @Test
    void testGetOrderById() throws Exception {
        when(service.getOrderById(101)).thenReturn(dto);

        mockMvc.perform(get("/api/orders/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Processing"));
    }

    @Test
    void testCreateOrder() throws Exception {
        String json = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateOrder() throws Exception {
        String json = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/orders/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/api/orders/101"))
                .andExpect(status().isNoContent());
    }
}
