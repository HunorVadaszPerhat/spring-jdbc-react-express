package com.example.springjdbc.dto.orderdetail;

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
public class OrderDetailDTO {
    @Min(value = 1, message = "Order number must be positive")
    private int orderNumber;

    @NotBlank(message = "Product code is required")
    private String productCode;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantityOrdered;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal priceEach;

    @Min(value = 1, message = "Order line number must be positive")
    private int orderLineNumber;
}
