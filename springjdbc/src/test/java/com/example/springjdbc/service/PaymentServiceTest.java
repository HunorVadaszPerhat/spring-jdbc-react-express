package com.example.springjdbc.service;

import com.example.springjdbc.dto.payment.PaymentDTO;
import com.example.springjdbc.mapper.payment.PaymentMapper;
import com.example.springjdbc.model.payment.Payment;
import com.example.springjdbc.repository.payment.PaymentRepository;
import com.example.springjdbc.service.payment.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository repository;

    @Mock
    private PaymentMapper mapper;

    @InjectMocks
    private PaymentService service;

    private Payment entity;
    private PaymentDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        entity = new Payment(103, "CHK123", LocalDate.now(), new BigDecimal("250.00"));
        dto = new PaymentDTO(103, "CHK123", entity.getPaymentDate(), entity.getAmount());
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);
        List<PaymentDTO> result = service.getAll();
        assertEquals(1, result.size());
    }

    @Test
    void testGetById() {
        when(repository.findById(103, "CHK123")).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);
        PaymentDTO result = service.getById(103, "CHK123");
        assertEquals("CHK123", result.getCheckNumber());
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
        service.delete(103, "CHK123");
        verify(repository).delete(103, "CHK123");
    }
}
