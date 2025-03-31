package com.example.springjdbc.controller.payment;

import com.example.springjdbc.dto.payment.PaymentDTO;
import com.example.springjdbc.service.payment.PaymentId;
import com.example.springjdbc.service.payment.PaymentModelAssembler;
import com.example.springjdbc.service.payment.PaymentService;
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
@RequestMapping("/payments")
@Tag(name = "Payment API", description = "Operations related to customer payments")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService service;
    private final PaymentModelAssembler assembler;

    public PaymentController(PaymentService service, PaymentModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Get all payments")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PaymentDTO>>> getAll() {
        log.info("Fetching all payments");
        List<PaymentDTO> list = service.getAll();

        List<EntityModel<PaymentDTO>> models = list.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(models,
                linkTo(methodOn(PaymentController.class).getAll()).withSelfRel()));
    }

    @Operation(summary = "Get payment by customerNumber and checkNumber")
    @GetMapping("/{customerNumber}/{checkNumber}")
    public ResponseEntity<EntityModel<PaymentDTO>> getById(
            @PathVariable int customerNumber,
            @PathVariable String checkNumber) {

        PaymentId id = new PaymentId(customerNumber, checkNumber);
        log.info("Fetching payment with ID: {}", id);
        PaymentDTO dto = service.getById(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @Operation(summary = "Create a new payment")
    @PostMapping
    public ResponseEntity<EntityModel<PaymentDTO>> create(@Valid @RequestBody PaymentDTO dto) {
        log.info("Creating new payment: {}", dto);
        service.create(dto);

        PaymentId id = new PaymentId(dto.getCustomerNumber(), dto.getCheckNumber());
        URI uri = linkTo(methodOn(PaymentController.class).getById(id.getCustomerNumber(), id.getCheckNumber())).toUri();
        return ResponseEntity.created(uri).body(assembler.toModel(dto));
    }

    @Operation(summary = "Update existing payment")
    @PutMapping("/{customerNumber}/{checkNumber}")
    public ResponseEntity<EntityModel<PaymentDTO>> update(
            @PathVariable int customerNumber,
            @PathVariable String checkNumber,
            @Valid @RequestBody PaymentDTO dto) {

        dto.setCustomerNumber(customerNumber);
        dto.setCheckNumber(checkNumber);

        log.info("Updating payment: {}", dto);
        service.update(dto);

        PaymentDTO updated = service.getById(new PaymentId(customerNumber, checkNumber));
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @Operation(summary = "Delete a payment")
    @DeleteMapping("/{customerNumber}/{checkNumber}")
    public ResponseEntity<Void> delete(
            @PathVariable int customerNumber,
            @PathVariable String checkNumber) {

        PaymentId id = new PaymentId(customerNumber, checkNumber);
        log.info("Deleting payment: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
