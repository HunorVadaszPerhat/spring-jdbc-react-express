package com.example.springjdbc.service.orderdetail;

import com.example.springjdbc.dto.orderdetail.OrderDetailDTO;
import com.example.springjdbc.exception.EntityNotFoundException;
import com.example.springjdbc.mapper.orderdetail.OrderDetailMapper;
import com.example.springjdbc.model.orderdetail.OrderDetail;
import com.example.springjdbc.repository.orderdetail.OrderDetailRepository;
import com.example.springjdbc.util.AbstractCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl extends AbstractCrudService<OrderDetailDTO, OrderDetail, OrderDetailId>
        implements OrderDetailService {

    private static final Logger log = LoggerFactory.getLogger(OrderDetailServiceImpl.class);

    private final OrderDetailRepository repository;
    private final OrderDetailMapper mapper;

    public OrderDetailServiceImpl(OrderDetailRepository repository, OrderDetailMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected List<OrderDetail> findAllEntities() {
        log.debug("Fetching all order details");
        return repository.findAll();
    }

    @Override
    protected Optional<OrderDetail> findEntityById(OrderDetailId id) {
        log.debug("Fetching order detail for orderNumber={}, productCode={}", id.getOrderNumber(), id.getProductCode());
        return repository.findById(id.getOrderNumber(), id.getProductCode());
    }

    @Override
    protected void saveEntity(OrderDetail entity) {
        log.debug("Saving order detail: {}", entity);
        repository.save(entity);
    }

    @Override
    protected void updateEntity(OrderDetail entity) {
        log.debug("Updating order detail: {}", entity);
        repository.update(entity);
    }

    @Override
    protected void deleteEntityById(OrderDetailId id) {
        log.debug("Deleting order detail: orderNumber={}, productCode={}", id.getOrderNumber(), id.getProductCode());
        repository.delete(id.getOrderNumber(), id.getProductCode());
    }

    @Override
    protected OrderDetailDTO toDTO(OrderDetail entity) {
        return mapper.toDTO(entity);
    }

    @Override
    protected OrderDetail toEntity(OrderDetailDTO dto) {
        return mapper.toEntity(dto);
    }
}
