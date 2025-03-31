package com.example.springjdbc.service.orderdetail;

import com.example.springjdbc.controller.orderdetail.OrderDetailController;
import com.example.springjdbc.dto.orderdetail.OrderDetailDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderDetailModelAssembler implements RepresentationModelAssembler<OrderDetailDTO, EntityModel<OrderDetailDTO>> {

    @Override
    public EntityModel<OrderDetailDTO> toModel(OrderDetailDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(OrderDetailController.class)
                        .getById(dto.getOrderNumber(), dto.getProductCode()))
                        .withSelfRel()
        );
    }
}
