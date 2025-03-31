package com.example.springjdbc.service.order;

import com.example.springjdbc.dto.order.OrderDTO;
import com.example.springjdbc.exception.EntityNotFoundException;
import com.example.springjdbc.mapper.order.OrderMapper;
import com.example.springjdbc.model.order.Order;
import com.example.springjdbc.repository.order.OrderRepository;
import com.example.springjdbc.util.AbstractCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends AbstractCrudService<OrderDTO, Order, Integer>
        implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository repository;
    private final OrderMapper mapper;

    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // === Abstract Method Implementations ===

    @Override
    protected List<Order> findAllEntities() {
        log.debug("Fetching all orders");
        return repository.findAll();
    }

    @Override
    protected Optional<Order> findEntityById(Integer id) {
        log.debug("Finding order by ID: {}", id);
        return repository.findById(id);
    }

    @Override
    protected void saveEntity(Order entity) {
        log.debug("Saving order: {}", entity);
        repository.save(entity);
    }

    @Override
    protected void updateEntity(Order entity) {
        log.debug("Updating order: {}", entity);
        repository.update(entity);
    }

    @Override
    protected void deleteEntityById(Integer id) {
        log.debug("Deleting order with ID: {}", id);
        repository.delete(id);
    }

    @Override
    protected OrderDTO toDTO(Order entity) {
        return mapper.toDTO(entity);
    }

    @Override
    protected Order toEntity(OrderDTO dto) {
        return mapper.toEntity(dto);
    }
}