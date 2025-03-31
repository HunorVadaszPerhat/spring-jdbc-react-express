package com.example.springjdbc.controller.productline;

import com.example.springjdbc.dto.productline.ProductLineDTO;
import com.example.springjdbc.service.productline.ProductLineModelAssembler;
import com.example.springjdbc.service.productline.ProductLineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/product-lines")
@Tag(name = "ProductLine API", description = "Operations related to product lines")
public class ProductLineController {

    private static final Logger log = LoggerFactory.getLogger(ProductLineController.class);

    private final ProductLineService service;
    private final ProductLineModelAssembler assembler;

    public ProductLineController(ProductLineService service, ProductLineModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Get all product lines")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductLineDTO>>> getAll() {
        log.info("Fetching all product lines");

        List<EntityModel<ProductLineDTO>> models = service.getAll().stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(ProductLineController.class).getAll()).withSelfRel()));
    }

    @Operation(summary = "Get product line by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductLineDTO>> getById(@PathVariable String id) {
        log.info("Fetching product line with ID: {}", id);
        ProductLineDTO dto = service.getById(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @Operation(summary = "Create a new product line")
    @PostMapping
    public ResponseEntity<EntityModel<ProductLineDTO>> create(@Valid @RequestBody ProductLineDTO dto) {
        log.info("Creating new product line: {}", dto);
        service.create(dto);

        URI uri = linkTo(methodOn(ProductLineController.class).getById(dto.getProductLine())).toUri();
        return ResponseEntity.created(uri).body(assembler.toModel(dto));
    }

    @Operation(summary = "Update a product line")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProductLineDTO>> update(@PathVariable String id,
                                                              @Valid @RequestBody ProductLineDTO dto) {
        dto.setProductLine(id);
        log.info("Updating product line with ID: {}", id);
        service.update(dto);

        ProductLineDTO updated = service.getById(id);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @Operation(summary = "Delete a product line")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting product line with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
