package com.example.springjdbc.service;

import com.example.springjdbc.dto.product.ProductDTO;
import com.example.springjdbc.mapper.product.ProductMapper;
import com.example.springjdbc.model.product.Product;
import com.example.springjdbc.repository.product.ProductRepository;
import com.example.springjdbc.service.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductService service;

    private Product entity;
    private ProductDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entity = new Product(
                "S10_1678", "1969 Harley Davidson", "Motorcycles",
                "1:10", "Min Lin Diecast",
                "Highly detailed model", 7933,
                new BigDecimal("48.81"), new BigDecimal("95.70")
        );
        dto = new ProductDTO(
                "S10_1678", "1969 Harley Davidson", "Motorcycles",
                "1:10", "Min Lin Diecast",
                "Highly detailed model", 7933,
                new BigDecimal("48.81"), new BigDecimal("95.70")
        );
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);
        List<ProductDTO> result = service.getAll();
        assertEquals(1, result.size());
        assertEquals("S10_1678", result.get(0).getProductCode());
    }

    @Test
    void testGetById() {
        when(repository.findById("S10_1678")).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);
        ProductDTO result = service.getById("S10_1678");
        assertEquals("1969 Harley Davidson", result.getProductName());
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
        service.delete("S10_1678");
        verify(repository).delete("S10_1678");
    }
}
