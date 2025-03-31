package com.example.springjdbc.mapper.productline;

import com.example.springjdbc.model.productline.ProductLine;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductLineRowMapper implements RowMapper<ProductLine> {
    @Override
    public ProductLine mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ProductLine(
                rs.getString("productLine"),
                rs.getString("textDescription"),
                rs.getString("htmlDescription"),
                rs.getBytes("image")
        );
    }
}
