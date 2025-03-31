package com.example.springjdbc.service;

import com.example.springjdbc.dto.employee.EmployeeDTO;
import com.example.springjdbc.mapper.employee.EmployeeMapper;
import com.example.springjdbc.model.employee.Employee;
import com.example.springjdbc.repository.employee.EmployeeRepository;
import com.example.springjdbc.service.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @Mock
    private EmployeeMapper mapper;

    @InjectMocks
    private EmployeeService service;

    private Employee employee;
    private EmployeeDTO dto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(1, "Doe", "John", "x123", "john.doe@example.com", "1", null, "Manager");
        dto = new EmployeeDTO(1, "Doe", "John", "x123", "john.doe@example.com", "1", null, "Manager");
    }

    @Test
    void testGetAllEmployees() {
        when(repository.findAll()).thenReturn(List.of(employee));
        when(mapper.toDTO(employee)).thenReturn(dto);
        List<EmployeeDTO> result = service.getAllEmployees();
        assertEquals(1, result.size());
    }

    @Test
    void testGetEmployeeById() {
        when(repository.findById(1)).thenReturn(Optional.of(employee));
        when(mapper.toDTO(employee)).thenReturn(dto);
        EmployeeDTO result = service.getEmployeeById(1);
        assertEquals("Doe", result.getLastName());
    }

    @Test
    void testCreateEmployee() {
        when(mapper.toEntity(dto)).thenReturn(employee);
        service.createEmployee(dto);
        verify(repository).save(employee);
    }

    @Test
    void testUpdateEmployee() {
        when(mapper.toEntity(dto)).thenReturn(employee);
        service.updateEmployee(dto);
        verify(repository).update(employee);
    }

    @Test
    void testDeleteEmployee() {
        service.deleteEmployee(1);
        verify(repository).delete(1);
    }
}
