package com.example.springjdbc.service.office;

import com.example.springjdbc.dto.office.OfficeDTO;
import com.example.springjdbc.exception.EntityNotFoundException;
import com.example.springjdbc.mapper.office.OfficeMapper;
import com.example.springjdbc.model.office.Office;
import com.example.springjdbc.repository.office.OfficeRepository;
import com.example.springjdbc.util.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public interface OfficeService extends CrudService<OfficeDTO, String> {
    // Add any office-specific methods here if needed
}