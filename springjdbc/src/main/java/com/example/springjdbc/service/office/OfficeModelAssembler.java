package com.example.springjdbc.service.office;

import com.example.springjdbc.controller.office.OfficeController;
import com.example.springjdbc.dto.office.OfficeDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OfficeModelAssembler implements RepresentationModelAssembler<OfficeDTO, EntityModel<OfficeDTO>> {

    @Override
    public EntityModel<OfficeDTO> toModel(OfficeDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(OfficeController.class).getById(dto.getOfficeCode())).withSelfRel(),
                linkTo(methodOn(OfficeController.class).getAll()).withRel("offices"));
    }
}
