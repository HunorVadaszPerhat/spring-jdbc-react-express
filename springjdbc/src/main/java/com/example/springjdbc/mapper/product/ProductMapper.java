package com.example.springjdbc.mapper.product;

import com.example.springjdbc.dto.product.ProductDTO;
import com.example.springjdbc.model.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO toDTO(Product p) {
        return new ProductDTO(
                p.getProductCode(),
                p.getProductName(),
                p.getProductLine(),
                p.getProductScale(),
                p.getProductVendor(),
                p.getProductDescription(),
                p.getQuantityInStock(),
                p.getBuyPrice(),
                p.getMsrp()
        );
    }

    public Product toEntity(ProductDTO dto) {
        return new Product(
                dto.getProductCode(),
                dto.getProductName(),
                dto.getProductLine(),
                dto.getProductScale(),
                dto.getProductVendor(),
                dto.getProductDescription(),
                dto.getQuantityInStock(),
                dto.getBuyPrice(),
                dto.getMsrp()
        );
    }
}
