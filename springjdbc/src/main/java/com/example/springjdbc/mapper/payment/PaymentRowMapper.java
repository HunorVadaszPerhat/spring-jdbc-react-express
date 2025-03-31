package com.example.springjdbc.mapper.payment;

import com.example.springjdbc.model.payment.Payment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class PaymentRowMapper implements RowMapper<Payment> {
    @Override
    public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Payment(
                rs.getInt("customerNumber"),
                rs.getString("checkNumber"),
                rs.getObject("paymentDate", LocalDate.class),
                rs.getBigDecimal("amount")
        );
    }
}