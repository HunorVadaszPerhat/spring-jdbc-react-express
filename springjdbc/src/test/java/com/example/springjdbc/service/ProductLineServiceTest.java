package com.example.springjdbc.service;

import com.example.springjdbc.dto.productline.ProductLineDTO;
import com.example.springjdbc.mapper.productline.ProductLineMapper;
import com.example.springjdbc.model.productline.ProductLine;
import com.example.springjdbc.repository.productline.ProductLineRepository;
import com.example.springjdbc.service.productline.ProductLineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductLineServiceTest {

    @Mock
    private ProductLineRepository repository;

    @Mock
    private ProductLineMapper mapper;

    @InjectMocks
    private ProductLineService service;

    private ProductLine entity;
    private ProductLineDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entity = new ProductLine("Classic Cars", "Cool vintage cars", "<p>HTML desc</p>", null);
        dto = new ProductLineDTO("Classic Cars", "Cool vintage cars", "<p>HTML desc</p>", null);
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);
        List<ProductLineDTO> result = service.getAll();
        assertEquals(1, result.size());
        assertEquals("Classic Cars", result.get(0).getProductLine());
    }

    @Test
    void testGetById() {
        when(repository.findById("Classic Cars")).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);
        ProductLineDTO result = service.getById("Classic Cars");
        assertEquals("Cool vintage cars", result.getTextDescription());
    }

    @Test
    void testCreate() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        service.create(dto);
        verify(repository).save(entity);
    }

    @Test
    void testUpdate() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        service.update(dto);
        verify(repository).update(entity);
    }

    @Test
    void testDelete() {
        service.delete("Classic Cars");
        verify(repository).delete("Classic Cars");
    }
}
