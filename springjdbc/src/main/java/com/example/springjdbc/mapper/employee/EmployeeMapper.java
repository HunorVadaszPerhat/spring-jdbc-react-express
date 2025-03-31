package com.example.springjdbc.mapper.employee;

import com.example.springjdbc.dto.employee.EmployeeDTO;
import com.example.springjdbc.model.employee.Employee;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeMapper {
    public EmployeeDTO toDTO(Employee e) {
        return new EmployeeDTO(
                e.getEmployeeNumber(),
                e.getLastName(),
                e.getFirstName(),
                e.getExtension(),
                e.getEmail(),
                e.getOfficeCode(),
                e.getReportsTo(),
                e.getJobTitle()
        );
    }

    public Employee toEntity(EmployeeDTO dto) {
        return new Employee(
                dto.getEmployeeNumber(),
                dto.getLastName(),
                dto.getFirstName(),
                dto.getExtension(),
                dto.getEmail(),
                dto.getOfficeCode(),
                dto.getReportsTo(),
                dto.getJobTitle()
        );
    }
}
