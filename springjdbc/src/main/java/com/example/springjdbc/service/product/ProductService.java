package com.example.springjdbc.service.product;

import com.example.springjdbc.dto.product.ProductDTO;
import com.example.springjdbc.exception.EntityNotFoundException;
import com.example.springjdbc.mapper.product.ProductMapper;
import com.example.springjdbc.model.product.Product;
import com.example.springjdbc.repository.product.ProductRepository;
import com.example.springjdbc.util.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public interface ProductService extends CrudService<ProductDTO, String> {
    // Add custom methods if needed
}