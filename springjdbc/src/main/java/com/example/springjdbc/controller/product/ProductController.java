package com.example.springjdbc.controller.product;

import com.example.springjdbc.dto.product.ProductDTO;
import com.example.springjdbc.service.product.ProductModelAssembler;
import com.example.springjdbc.service.product.ProductService;
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
@RequestMapping("/products")
@Tag(name = "Product API", description = "Operations related to products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService service;
    private final ProductModelAssembler assembler;

    public ProductController(ProductService service, ProductModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Get all products")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> getAll() {
        log.info("Fetching all products");

        List<EntityModel<ProductDTO>> models = service.getAll().stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(ProductController.class).getAll()).withSelfRel()));
    }

    @Operation(summary = "Get product by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductDTO>> getById(@PathVariable String id) {
        log.info("Fetching product with ID: {}", id);
        ProductDTO dto = service.getById(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @Operation(summary = "Create a new product")
    @PostMapping
    public ResponseEntity<EntityModel<ProductDTO>> create(@Valid @RequestBody ProductDTO dto) {
        log.info("Creating new product: {}", dto);
        service.create(dto);

        URI uri = linkTo(methodOn(ProductController.class).getById(dto.getProductCode())).toUri();
        return ResponseEntity.created(uri).body(assembler.toModel(dto));
    }

    @Operation(summary = "Update a product")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProductDTO>> update(@PathVariable String id,
                                                          @Valid @RequestBody ProductDTO dto) {
        dto.setProductCode(id);
        log.info("Updating product with ID: {}", id);
        service.update(dto);

        ProductDTO updated = service.getById(id);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @Operation(summary = "Delete a product")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting product with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
