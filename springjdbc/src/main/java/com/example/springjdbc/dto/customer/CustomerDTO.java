package com.example.springjdbc.dto.customer;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private int customerNumber;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Contact name is required")
    private String contactName;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    @DecimalMin(value = "0.0", inclusive = true, message = "Credit limit must be non-negative")
    private BigDecimal creditLimit;
}