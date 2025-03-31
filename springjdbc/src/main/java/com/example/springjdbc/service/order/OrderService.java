package com.example.springjdbc.service.order;

import com.example.springjdbc.dto.order.OrderDTO;
import com.example.springjdbc.exception.EntityNotFoundException;
import com.example.springjdbc.mapper.order.OrderMapper;
import com.example.springjdbc.model.order.Order;
import com.example.springjdbc.repository.order.OrderRepository;
import com.example.springjdbc.util.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public interface OrderService extends CrudService<OrderDTO, Integer> {
    // Add order-specific service methods here if needed
}
