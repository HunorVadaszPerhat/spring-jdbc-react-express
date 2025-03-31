package com.example.springjdbc.controller.order;

import com.example.springjdbc.dto.order.OrderDTO;
import com.example.springjdbc.service.order.OrderModelAssembler;
import com.example.springjdbc.service.order.OrderService;
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
@RequestMapping("/orders")
@Tag(name = "Order API", description = "Endpoints for managing orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService service;
    private final OrderModelAssembler assembler;

    public OrderController(OrderService service, OrderModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Get all orders")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getAll() {
        log.info("Fetching all orders");
        List<OrderDTO> orders = service.getAll();

        List<EntityModel<OrderDTO>> orderModels = orders.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(orderModels,
                linkTo(methodOn(OrderController.class).getAll()).withSelfRel()));
    }

    @Operation(summary = "Get an order by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OrderDTO>> getById(@PathVariable int id) {
        log.info("Fetching order with ID: {}", id);
        OrderDTO dto = service.getById(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @Operation(summary = "Create a new order")
    @PostMapping
    public ResponseEntity<EntityModel<OrderDTO>> create(@Valid @RequestBody OrderDTO dto) {
        log.info("Creating order: {}", dto);
        service.create(dto);
        OrderDTO created = service.getById(dto.getOrderNumber());

        return ResponseEntity.created(linkTo(methodOn(OrderController.class)
                        .getById(created.getOrderNumber())).toUri())
                .body(assembler.toModel(created));
    }

    @Operation(summary = "Update an existing order")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<OrderDTO>> update(@PathVariable int id, @Valid @RequestBody OrderDTO dto) {
        log.info("Updating order with ID: {}", id);
        dto.setOrderNumber(id);
        service.update(dto);
        OrderDTO updated = service.getById(id);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @Operation(summary = "Delete an order by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        log.info("Deleting order with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
