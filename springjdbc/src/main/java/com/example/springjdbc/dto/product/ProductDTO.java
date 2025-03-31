package com.example.springjdbc.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotBlank(message = "Product code is required")
    private String productCode;

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotBlank(message = "Product line is required")
    private String productLine;

    @NotBlank(message = "Scale is required")
    private String productScale;

    @NotBlank(message = "Vendor is required")
    private String productVendor;

    @NotBlank(message = "Description is required")
    private String productDescription;

    @Min(value = 0, message = "Stock quantity must be non-negative")
    private int quantityInStock;

    @DecimalMin(value = "0.01", message = "Buy price must be greater than 0")
    private BigDecimal buyPrice;

    @DecimalMin(value = "0.01", message = "MSRP must be greater than 0")
    private BigDecimal msrp;
}
