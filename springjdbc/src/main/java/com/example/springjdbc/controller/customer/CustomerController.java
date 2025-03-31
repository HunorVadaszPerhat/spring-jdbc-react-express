package com.example.springjdbc.controller.customer;

import com.example.springjdbc.dto.customer.CustomerDTO;
import com.example.springjdbc.service.customer.CustomerModelAssembler;
import com.example.springjdbc.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
// --- CustomerController with HATEOAS and Exception Handling ---

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer API", description = "Operations related to customer management")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService service;
    private final CustomerModelAssembler assembler;

    public CustomerController(CustomerService service,
                              CustomerModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        log.info(">>> test() endpoint hit");
        return ResponseEntity.ok(List.of());
    }

    @Operation(summary = "Get paginated list of customers")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CustomerDTO>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Fetching all customers with page={}, size={}", page, size);
        List<CustomerDTO> customers = service.getAllCustomers(page, size); // Custom paginated logic
        List<EntityModel<CustomerDTO>> customerModels = customers.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(customerModels,
                linkTo(methodOn(CustomerController.class).getAll(page, size)).withSelfRel()));
    }

    @Operation(summary = "Get a customer by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CustomerDTO>> getById(@PathVariable int id) {
        log.info("Fetching customer with ID {}", id);
        CustomerDTO dto = service.getById(id); // ✅ inherited from AbstractCrudService
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @Operation(summary = "Create a new customer")
    @PostMapping
    public ResponseEntity<EntityModel<CustomerDTO>> create(@Valid @RequestBody CustomerDTO dto) {
        log.info("Creating new customer: {}", dto);
        service.create(dto); // ✅ inherited
        CustomerDTO created = service.getById(dto.getCustomerNumber()); // fetch latest version
        return ResponseEntity.created(linkTo(methodOn(CustomerController.class)
                        .getById(created.getCustomerNumber())).toUri())
                .body(assembler.toModel(created));
    }

    @Operation(summary = "Update an existing customer")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CustomerDTO>> update(@PathVariable int id,
                                                           @Valid @RequestBody CustomerDTO dto) {
        log.info("Updating customer with ID: {}", id);
        dto.setCustomerNumber(id); // ensure ID is set from path
        service.update(dto); // ✅ inherited
        CustomerDTO updated = service.getById(id);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @Operation(summary = "Delete a customer by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        log.info("Deleting customer with ID: {}", id);
        service.delete(id); // ✅ inherited
        return ResponseEntity.noContent().build();
    }
}