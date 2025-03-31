package com.example.springjdbc.service;

import com.example.springjdbc.dto.order.OrderDTO;
import com.example.springjdbc.mapper.order.OrderMapper;
import com.example.springjdbc.model.order.Order;
import com.example.springjdbc.repository.order.OrderRepository;
import com.example.springjdbc.service.order.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private OrderMapper mapper;

    @InjectMocks
    private OrderService service;

    private Order order;
    private OrderDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new Order(101, LocalDate.now(), LocalDate.now().plusDays(3), null, "Processing", "Handle with care", 1);
        dto = new OrderDTO(101, order.getOrderDate(), order.getRequiredDate(), null, "Processing", "Handle with care", 1);
    }

    @Test
    void testGetAllOrders() {
        when(repository.findAll()).thenReturn(List.of(order));
        when(mapper.toDTO(order)).thenReturn(dto);
        List<OrderDTO> result = service.getAllOrders();
        assertEquals(1, result.size());
    }

    @Test
    void testGetOrderById() {
        when(repository.findById(101)).thenReturn(Optional.of(order));
        when(mapper.toDTO(order)).thenReturn(dto);
        OrderDTO result = service.getOrderById(101);
        assertEquals("Processing", result.getStatus());
    }

    @Test
    void testCreateOrder() {
        when(mapper.toEntity(dto)).thenReturn(order);
        service.createOrder(dto);
        verify(repository).save(order);
    }

    @Test
    void testUpdateOrder() {
        when(mapper.toEntity(dto)).thenReturn(order);
        service.updateOrder(dto);
        verify(repository).update(order);
    }

    @Test
    void testDeleteOrder() {
        service.deleteOrder(101);
        verify(repository).delete(101);
    }
}
