package com.example.springjdbc.mapper.office;

import com.example.springjdbc.dto.office.OfficeDTO;
import com.example.springjdbc.model.office.Office;
import org.springframework.stereotype.Component;

@Component
public class OfficeMapper {
    public OfficeDTO toDTO(Office o) {
        return new OfficeDTO(
                o.getOfficeCode(),
                o.getCity(),
                o.getPhone(),
                o.getAddressLine1(),
                o.getAddressLine2(),
                o.getState(),
                o.getCountry(),
                o.getPostalCode(),
                o.getTerritory()
        );
    }

    public Office toEntity(OfficeDTO dto) {
        return new Office(
                dto.getOfficeCode(),
                dto.getCity(),
                dto.getPhone(),
                dto.getAddressLine1(),
                dto.getAddressLine2(),
                dto.getState(),
                dto.getCountry(),
                dto.getPostalCode(),
                dto.getTerritory()
        );
    }
}

