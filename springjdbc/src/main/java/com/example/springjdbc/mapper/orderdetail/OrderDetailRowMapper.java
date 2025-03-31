package com.example.springjdbc.mapper.orderdetail;

import com.example.springjdbc.dto.orderdetail.OrderDetailDTO;
import com.example.springjdbc.model.orderdetail.OrderDetail;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderDetailRowMapper implements RowMapper<OrderDetail> {
    @Override
    public OrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderDetail(
                rs.getInt("orderNumber"),
                rs.getString("productCode"),
                rs.getInt("quantityOrdered"),
                rs.getBigDecimal("priceEach"),
                rs.getInt("orderLineNumber")
        );
    }
}


