package com.example.springjdbc.service.productline;

import com.example.springjdbc.dto.productline.ProductLineDTO;
import com.example.springjdbc.exception.EntityNotFoundException;
import com.example.springjdbc.mapper.productline.ProductLineMapper;
import com.example.springjdbc.model.productline.ProductLine;
import com.example.springjdbc.repository.productline.ProductLineRepository;
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

@Service
public class ProductLineServiceImpl extends AbstractCrudService<ProductLineDTO, ProductLine, String>
        implements ProductLineService {

    private static final Logger log = LoggerFactory.getLogger(ProductLineServiceImpl.class);

    private final ProductLineRepository repository;
    private final ProductLineMapper mapper;

    public ProductLineServiceImpl(ProductLineRepository repository, ProductLineMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected List<ProductLine> findAllEntities() {
        log.debug("Fetching all product lines");
        return repository.findAll();
    }

    @Override
    protected Optional<ProductLine> findEntityById(String id) {
        log.debug("Fetching product line by ID: {}", id);
        return repository.findById(id);
    }

    @Override
    protected void saveEntity(ProductLine entity) {
        log.debug("Saving product line: {}", entity);
        repository.save(entity);
    }

    @Override
    protected void updateEntity(ProductLine entity) {
        log.debug("Updating product line: {}", entity);
        repository.update(entity);
    }

    @Override
    protected void deleteEntityById(String id) {
        log.debug("Deleting product line by ID: {}", id);
        repository.delete(id);
    }

    @Override
    protected ProductLineDTO toDTO(ProductLine entity) {
        return mapper.toDTO(entity);
    }

    @Override
    protected ProductLine toEntity(ProductLineDTO dto) {
        return mapper.toEntity(dto);
    }
}
