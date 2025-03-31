package com.example.springjdbc.service.employee;

import com.example.springjdbc.dto.employee.EmployeeDTO;
import com.example.springjdbc.exception.EntityNotFoundException;
import com.example.springjdbc.mapper.employee.EmployeeMapper;
import com.example.springjdbc.model.employee.Employee;
import com.example.springjdbc.repository.employee.EmployeeRepository;
import com.example.springjdbc.util.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public interface EmployeeService extends CrudService<EmployeeDTO, Integer> {
    // Add custom employee-specific methods if needed
}
