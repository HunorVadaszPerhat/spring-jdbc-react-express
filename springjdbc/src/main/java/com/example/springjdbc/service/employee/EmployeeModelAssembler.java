package com.example.springjdbc.service.employee;

import com.example.springjdbc.controller.employee.EmployeeController;
import com.example.springjdbc.dto.employee.EmployeeDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<EmployeeDTO, EntityModel<EmployeeDTO>> {

    @Override
    public EntityModel<EmployeeDTO> toModel(EmployeeDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(EmployeeController.class).getById(dto.getEmployeeNumber())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getAll()).withRel("employees"));
    }
}
