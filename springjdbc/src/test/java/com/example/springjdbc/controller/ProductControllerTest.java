package com.example.springjdbc.controller;

import com.example.springjdbc.controller.product.ProductController;
import com.example.springjdbc.dto.product.ProductDTO;
import com.example.springjdbc.service.product.ProductService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    private ProductDTO dto;

    @BeforeEach
    void setUp() {
        dto = new ProductDTO(
                "S10_1678", "1969 Harley Davidson", "Motorcycles",
                "1:10", "Min Lin Diecast",
                "Highly detailed model", 7933,
                new BigDecimal("48.81"), new BigDecimal("95.70")
        );
    }

    @Test
    void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productCode").value("S10_1678"));
    }

    @Test
    void testGetById() throws Exception {
        when(service.getById("S10_1678")).thenReturn(dto);

        mockMvc.perform(get("/api/products/S10_1678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("1969 Harley Davidson"));
    }

    @Test
    void testCreate() throws Exception {
        String json = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        String json = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/products/S10_1678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/products/S10_1678"))
                .andExpect(status().isNoContent());
    }
}
