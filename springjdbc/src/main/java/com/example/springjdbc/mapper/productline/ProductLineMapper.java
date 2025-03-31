package com.example.springjdbc.mapper.productline;

import com.example.springjdbc.dto.productline.ProductLineDTO;
import com.example.springjdbc.model.productline.ProductLine;
import org.springframework.stereotype.Component;

@Component
public class ProductLineMapper {
    public ProductLineDTO toDTO(ProductLine p) {
        return new ProductLineDTO(
                p.getProductLine(),
                p.getTextDescription(),
                p.getHtmlDescription(),
                p.getImage()
        );
    }

    public ProductLine toEntity(ProductLineDTO dto) {
        return new ProductLine(
                dto.getProductLine(),
                dto.getTextDescription(),
                dto.getHtmlDescription(),
                dto.getImage()
        );
    }
}
