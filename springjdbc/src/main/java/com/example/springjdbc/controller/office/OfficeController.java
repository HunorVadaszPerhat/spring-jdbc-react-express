package com.example.springjdbc.controller.office;

import com.example.springjdbc.dto.office.OfficeDTO;
import com.example.springjdbc.service.office.OfficeModelAssembler;
import com.example.springjdbc.service.office.OfficeService;
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
@RequestMapping("/offices")
@Tag(name = "Office API", description = "Operations related to offices")
public class OfficeController {

    private static final Logger log = LoggerFactory.getLogger(OfficeController.class);

    private final OfficeService service;
    private final OfficeModelAssembler assembler;

    public OfficeController(OfficeService service, OfficeModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Get all offices")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<OfficeDTO>>> getAll() {
        log.info("Fetching all offices");
        List<OfficeDTO> offices = service.getAll();

        List<EntityModel<OfficeDTO>> officeModels = offices.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(officeModels,
                linkTo(methodOn(OfficeController.class).getAll()).withSelfRel()));
    }

    @Operation(summary = "Get an office by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OfficeDTO>> getById(@PathVariable String id) {
        log.info("Fetching office with ID: {}", id);
        OfficeDTO dto = service.getById(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @Operation(summary = "Create a new office")
    @PostMapping
    public ResponseEntity<EntityModel<OfficeDTO>> create(@Valid @RequestBody OfficeDTO dto) {
        log.info("Creating office: {}", dto);
        service.create(dto);
        OfficeDTO created = service.getById(dto.getOfficeCode());

        return ResponseEntity.created(linkTo(methodOn(OfficeController.class).getById(created.getOfficeCode())).toUri())
                .body(assembler.toModel(created));
    }

    @Operation(summary = "Update an existing office")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<OfficeDTO>> update(@PathVariable String id, @Valid @RequestBody OfficeDTO dto) {
        log.info("Updating office with ID: {}", id);
        dto.setOfficeCode(id);
        service.update(dto);
        OfficeDTO updated = service.getById(id);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @Operation(summary = "Delete an office")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting office with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
