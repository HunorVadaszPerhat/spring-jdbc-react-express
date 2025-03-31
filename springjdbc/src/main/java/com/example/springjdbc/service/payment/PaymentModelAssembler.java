package com.example.springjdbc.service.payment;

import com.example.springjdbc.controller.payment.PaymentController;
import com.example.springjdbc.dto.payment.PaymentDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaymentModelAssembler implements RepresentationModelAssembler<PaymentDTO, EntityModel<PaymentDTO>> {

    @Override
    public EntityModel<PaymentDTO> toModel(PaymentDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(PaymentController.class)
                        .getById(dto.getCustomerNumber(), dto.getCheckNumber())).withSelfRel(),
                linkTo(methodOn(PaymentController.class)
                        .getAll()).withRel("payments"));
    }
}