package com.example.springjdbc.service.payment;

import com.example.springjdbc.dto.payment.PaymentDTO;
import com.example.springjdbc.exception.EntityNotFoundException;
import com.example.springjdbc.mapper.payment.PaymentMapper;
import com.example.springjdbc.model.payment.Payment;
import com.example.springjdbc.repository.payment.PaymentRepository;
import com.example.springjdbc.util.AbstractCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class PaymentServiceImpl extends AbstractCrudService<PaymentDTO, Payment, PaymentId>
        implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    public PaymentServiceImpl(PaymentRepository repository, PaymentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected List<Payment> findAllEntities() {
        log.debug("Fetching all payments");
        return repository.findAll();
    }

    @Override
    protected Optional<Payment> findEntityById(PaymentId id) {
        log.debug("Fetching payment for customerNumber={}, checkNumber={}", id.getCustomerNumber(), id.getCheckNumber());
        return repository.findById(id.getCustomerNumber(), id.getCheckNumber());
    }

    @Override
    protected void saveEntity(Payment entity) {
        log.debug("Saving payment: {}", entity);
        repository.save(entity);
    }

    @Override
    protected void updateEntity(Payment entity) {
        log.debug("Updating payment: {}", entity);
        repository.update(entity);
    }

    @Override
    protected void deleteEntityById(PaymentId id) {
        log.debug("Deleting payment: customerNumber={}, checkNumber={}", id.getCustomerNumber(), id.getCheckNumber());
        repository.delete(id.getCustomerNumber(), id.getCheckNumber());
    }

    @Override
    protected PaymentDTO toDTO(Payment entity) {
        return mapper.toDTO(entity);
    }

    @Override
    protected Payment toEntity(PaymentDTO dto) {
        return mapper.toEntity(dto);
    }
}
