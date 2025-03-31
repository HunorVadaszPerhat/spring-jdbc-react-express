package com.example.springjdbc.service.product;

import com.example.springjdbc.controller.product.ProductController;
import com.example.springjdbc.dto.product.ProductDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<ProductDTO, EntityModel<ProductDTO>> {

    @Override
    public EntityModel<ProductDTO> toModel(ProductDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ProductController.class).getById(dto.getProductCode())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAll()).withRel("products"));
    }
}