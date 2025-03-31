package com.example.springjdbc.service.productline;

import com.example.springjdbc.dto.productline.ProductLineDTO;
import com.example.springjdbc.exception.EntityNotFoundException;
import com.example.springjdbc.mapper.productline.ProductLineMapper;
import com.example.springjdbc.model.productline.ProductLine;
import com.example.springjdbc.repository.productline.ProductLineRepository;
import com.example.springjdbc.util.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public interface ProductLineService extends CrudService<ProductLineDTO, String> {
    // Add custom business methods here if needed
}