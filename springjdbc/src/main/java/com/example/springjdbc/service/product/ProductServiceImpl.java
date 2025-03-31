package com.example.springjdbc.service.product;

import com.example.springjdbc.dto.product.ProductDTO;
import com.example.springjdbc.exception.EntityNotFoundException;
import com.example.springjdbc.mapper.product.ProductMapper;
import com.example.springjdbc.model.product.Product;
import com.example.springjdbc.repository.product.ProductRepository;
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
public class ProductServiceImpl extends AbstractCrudService<ProductDTO, Product, String>
        implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected List<Product> findAllEntities() {
        log.debug("Fetching all products");
        return repository.findAll();
    }

    @Override
    protected Optional<Product> findEntityById(String id) {
        log.debug("Fetching product by ID: {}", id);
        return repository.findById(id);
    }

    @Override
    protected void saveEntity(Product entity) {
        log.debug("Saving product: {}", entity);
        repository.save(entity);
    }

    @Override
    protected void updateEntity(Product entity) {
        log.debug("Updating product: {}", entity);
        repository.update(entity);
    }

    @Override
    protected void deleteEntityById(String id) {
        log.debug("Deleting product by ID: {}", id);
        repository.delete(id);
    }

    @Override
    protected ProductDTO toDTO(Product entity) {
        return mapper.toDTO(entity);
    }

    @Override
    protected Product toEntity(ProductDTO dto) {
        return mapper.toEntity(dto);
    }
}
