package com.example.springjdbc.service.productline;

import com.example.springjdbc.controller.productline.ProductLineController;
import com.example.springjdbc.dto.productline.ProductLineDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductLineModelAssembler implements RepresentationModelAssembler<ProductLineDTO, EntityModel<ProductLineDTO>> {

    @Override
    public EntityModel<ProductLineDTO> toModel(ProductLineDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ProductLineController.class).getById(dto.getProductLine())).withSelfRel(),
                linkTo(methodOn(ProductLineController.class).getAll()).withRel("product-lines"));
    }
}