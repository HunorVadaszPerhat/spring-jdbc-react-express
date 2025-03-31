package com.example.springjdbc.service;

import com.example.springjdbc.dto.orderdetail.OrderDetailDTO;
import com.example.springjdbc.mapper.orderdetail.OrderDetailMapper;
import com.example.springjdbc.model.orderdetail.OrderDetail;
import com.example.springjdbc.repository.orderdetail.OrderDetailRepository;
import com.example.springjdbc.service.orderdetail.OrderDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderDetailServiceTest {

    @Mock
    private OrderDetailRepository repository;

    @Mock
    private OrderDetailMapper mapper;

    @InjectMocks
    private OrderDetailService service;

    private OrderDetail entity;
    private OrderDetailDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        entity = new OrderDetail(10100, "S10_1678", 30, new BigDecimal("100.00"), 1);
        dto = new OrderDetailDTO(10100, "S10_1678", 30, new BigDecimal("100.00"), 1);
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);
        List<OrderDetailDTO> result = service.getAll();
        assertEquals(1, result.size());
        assertEquals("S10_1678", result.get(0).getProductCode());
    }

    @Test
    void testGetById() {
        when(repository.findById(10100, "S10_1678")).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);
        OrderDetailDTO result = service.getById(10100, "S10_1678");
        assertEquals(30, result.getQuantityOrdered());
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
        service.delete(10100, "S10_1678");
        verify(repository).delete(10100, "S10_1678");
    }
}
