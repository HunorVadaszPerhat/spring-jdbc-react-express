package com.example.springjdbc.service.orderdetail;

import com.example.springjdbc.dto.orderdetail.OrderDetailDTO;
import com.example.springjdbc.exception.EntityNotFoundException;
import com.example.springjdbc.mapper.orderdetail.OrderDetailMapper;
import com.example.springjdbc.model.orderdetail.OrderDetail;
import com.example.springjdbc.repository.orderdetail.OrderDetailRepository;
import com.example.springjdbc.util.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public interface OrderDetailService extends CrudService<OrderDetailDTO, OrderDetailId> {
    // Add custom methods if needed (e.g., find by orderNumber)
}