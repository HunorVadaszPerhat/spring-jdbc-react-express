package com.example.springjdbc.dto.payment;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    @Min(value = 1, message = "Customer number must be positive")
    private int customerNumber;

    @NotBlank(message = "Check number is required")
    private String checkNumber;

    @NotNull(message = "Payment date is required")
    private LocalDate paymentDate;

    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
}
