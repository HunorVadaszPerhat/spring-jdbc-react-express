package com.example.springjdbc.controller;

import com.example.springjdbc.controller.productline.ProductLineController;
import com.example.springjdbc.dto.productline.ProductLineDTO;
import com.example.springjdbc.service.productline.ProductLineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductLineController.class)
public class ProductLineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductLineService service;

    private ProductLineDTO dto;

    @BeforeEach
    void setUp() {
        dto = new ProductLineDTO("Classic Cars", "Cool vintage cars", "<p>HTML</p>", null);
    }

    @Test
    void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/product-lines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productLine").value("Classic Cars"));
    }

    @Test
    void testGetById() throws Exception {
        when(service.getById("Classic Cars")).thenReturn(dto);

        mockMvc.perform(get("/api/product-lines/Classic Cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.textDescription").value("Cool vintage cars"));
    }

    @Test
    void testCreate() throws Exception {
        String json = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/api/product-lines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        String json = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(put("/api/product-lines/Classic Cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/product-lines/Classic Cars"))
                .andExpect(status().isNoContent());
    }
}