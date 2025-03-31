package com.example.springjdbc.controller.employee;

import com.example.springjdbc.dto.employee.EmployeeDTO;
import com.example.springjdbc.service.employee.EmployeeModelAssembler;
import com.example.springjdbc.service.employee.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/employees")
@Tag(name = "Employee API", description = "Operations related to employees")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService service;
    private final EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeService service, EmployeeModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Get all employees")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<EmployeeDTO>>> getAll() {
        log.info("Fetching all employees");
        List<EmployeeDTO> employees = service.getAll();

        List<EntityModel<EmployeeDTO>> models = employees.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(EmployeeController.class).getAll()).withSelfRel()));
    }

    @Operation(summary = "Get an employee by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EmployeeDTO>> getById(@PathVariable int id) {
        log.info("Fetching employee with ID {}", id);
        EmployeeDTO dto = service.getById(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @Operation(summary = "Create a new employee")
    @PostMapping
    public ResponseEntity<EntityModel<EmployeeDTO>> create(@Valid @RequestBody EmployeeDTO dto) {
        log.info("Creating employee: {}", dto);
        service.create(dto);
        EmployeeDTO created = service.getById(dto.getEmployeeNumber());
        return ResponseEntity.created(linkTo(methodOn(EmployeeController.class).getById(created.getEmployeeNumber())).toUri())
                .body(assembler.toModel(created));
    }

    @Operation(summary = "Update an employee")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EmployeeDTO>> update(@PathVariable int id, @Valid @RequestBody EmployeeDTO dto) {
        log.info("Updating employee with ID: {}", id);
        dto.setEmployeeNumber(id);
        service.update(dto);
        EmployeeDTO updated = service.getById(id);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @Operation(summary = "Delete an employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        log.info("Deleting employee with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
