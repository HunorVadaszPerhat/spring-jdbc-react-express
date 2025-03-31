package com.example.springjdbc.service.payment;

import com.example.springjdbc.dto.payment.PaymentDTO;
import com.example.springjdbc.exception.EntityNotFoundException;
import com.example.springjdbc.mapper.payment.PaymentMapper;
import com.example.springjdbc.model.payment.Payment;
import com.example.springjdbc.repository.payment.PaymentRepository;
import com.example.springjdbc.util.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public interface PaymentService extends CrudService<PaymentDTO, PaymentId> {
    // Add custom methods here if needed (e.g., getByCustomerNumber(int customerNumber))
}
