package com.example.springjdbc.controller.orderdetail;

import com.example.springjdbc.dto.orderdetail.OrderDetailDTO;
import com.example.springjdbc.service.orderdetail.OrderDetailId;
import com.example.springjdbc.service.orderdetail.OrderDetailModelAssembler;
import com.example.springjdbc.service.orderdetail.OrderDetailService;
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
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/order-details")
@Tag(name = "Order Detail API", description = "Operations related to order details")
public class OrderDetailController {

    private static final Logger log = LoggerFactory.getLogger(OrderDetailController.class);

    private final OrderDetailService service;
    private final OrderDetailModelAssembler assembler;

    public OrderDetailController(OrderDetailService service, OrderDetailModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    @Operation(summary = "Get all order details")
    public ResponseEntity<CollectionModel<EntityModel<OrderDetailDTO>>> getAll() {
        log.info("Fetching all order details");
        List<OrderDetailDTO> list = service.getAll();

        List<EntityModel<OrderDetailDTO>> models = list.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(OrderDetailController.class).getAll()).withSelfRel()));
    }

    @GetMapping("/{orderNumber}/{productCode}")
    @Operation(summary = "Get order detail by orderNumber and productCode")
    public ResponseEntity<EntityModel<OrderDetailDTO>> getById(@PathVariable int orderNumber,
                                                               @PathVariable String productCode) {
        OrderDetailId id = new OrderDetailId(orderNumber, productCode);
        log.info("Fetching order detail with ID: {}", id);
        OrderDetailDTO dto = service.getById(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @PostMapping
    @Operation(summary = "Create a new order detail")
    public ResponseEntity<EntityModel<OrderDetailDTO>> create(@Valid @RequestBody OrderDetailDTO dto) {
        log.info("Creating new order detail: {}", dto);
        service.create(dto);

        OrderDetailId id = new OrderDetailId(dto.getOrderNumber(), dto.getProductCode());
        URI uri = linkTo(methodOn(OrderDetailController.class)
                .getById(id.getOrderNumber(), id.getProductCode()))
                .toUri();

        return ResponseEntity.created(uri).body(assembler.toModel(dto));
    }

    @PutMapping("/{orderNumber}/{productCode}")
    @Operation(summary = "Update existing order detail")
    public ResponseEntity<EntityModel<OrderDetailDTO>> update(@PathVariable int orderNumber,
                                                              @PathVariable String productCode,
                                                              @Valid @RequestBody OrderDetailDTO dto) {
        dto.setOrderNumber(orderNumber);
        dto.setProductCode(productCode);

        log.info("Updating order detail: {}", dto);
        service.update(dto);

        OrderDetailDTO updated = service.getById(new OrderDetailId(orderNumber, productCode));
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @DeleteMapping("/{orderNumber}/{productCode}")
    @Operation(summary = "Delete an order detail")
    public ResponseEntity<Void> delete(@PathVariable int orderNumber,
                                       @PathVariable String productCode) {
        OrderDetailId id = new OrderDetailId(orderNumber, productCode);
        log.info("Deleting order detail with ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}