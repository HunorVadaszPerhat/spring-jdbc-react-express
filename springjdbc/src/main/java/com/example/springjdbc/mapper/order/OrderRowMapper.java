package com.example.springjdbc.mapper.order;

import com.example.springjdbc.model.order.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Order(
                rs.getInt("orderNumber"),
                rs.getObject("orderDate", LocalDate.class),
                rs.getObject("requiredDate", LocalDate.class),
                rs.getObject("shippedDate", LocalDate.class),
                rs.getString("status"),
                rs.getString("comments"),
                rs.getInt("customerNumber")
        );
    }
}
