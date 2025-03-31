package com.example.springjdbc.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int orderNumber;

    @NotNull(message = "Order date is required")
    private LocalDate orderDate;

    @NotNull(message = "Required date is required")
    private LocalDate requiredDate;

    private LocalDate shippedDate;

    @NotBlank(message = "Status is required")
    private String status;

    private String comments;

    @Min(value = 1, message = "Customer number must be positive")
    private int customerNumber;
}
