package com.example.springjdbc.model.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private int customerNumber;
    private String checkNumber;
    private LocalDate paymentDate;
    private BigDecimal amount;
}
