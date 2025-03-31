package com.example.springjdbc.service.employee;

import com.example.springjdbc.dto.employee.EmployeeDTO;
import com.example.springjdbc.mapper.employee.EmployeeMapper;
import com.example.springjdbc.model.employee.Employee;
import com.example.springjdbc.repository.employee.EmployeeRepository;
import com.example.springjdbc.util.AbstractCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl extends AbstractCrudService<EmployeeDTO, Employee, Integer>
        implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    public EmployeeServiceImpl(EmployeeRepository repository, EmployeeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // === Custom logic can be added here ===
    // e.g., public List<EmployeeDTO> findByJobTitle(String title) { ... }

    // === Abstract Methods Implementations ===
    @Override
    protected List<Employee> findAllEntities() {
        log.debug("Fetching all employees");
        return repository.findAll();
    }

    @Override
    protected Optional<Employee> findEntityById(Integer id) {
        log.debug("Finding employee by ID: {}", id);
        return repository.findById(id);
    }

    @Override
    protected void saveEntity(Employee entity) {
        log.debug("Saving new employee: {}", entity);
        repository.save(entity);
    }

    @Override
    protected void updateEntity(Employee entity) {
        log.debug("Updating employee: {}", entity);
        repository.update(entity);
    }

    @Override
    protected void deleteEntityById(Integer id) {
        log.debug("Deleting employee with ID: {}", id);
        repository.delete(id);
    }

    @Override
    protected EmployeeDTO toDTO(Employee entity) {
        return mapper.toDTO(entity);
    }

    @Override
    protected Employee toEntity(EmployeeDTO dto) {
        return mapper.toEntity(dto);
    }
}