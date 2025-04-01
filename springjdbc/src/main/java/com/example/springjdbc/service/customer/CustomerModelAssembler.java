package com.example.springjdbc.service.customer;

import com.example.springjdbc.controller.customer.CustomerController;
import com.example.springjdbc.dto.customer.CustomerDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<CustomerDTO, EntityModel<CustomerDTO>> {

    @Override
    public EntityModel<CustomerDTO> toModel(CustomerDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(CustomerController.class).getById(dto.getCustomerNumber())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).getAll(0, 10)).withRel("customers")
        );
    }
}