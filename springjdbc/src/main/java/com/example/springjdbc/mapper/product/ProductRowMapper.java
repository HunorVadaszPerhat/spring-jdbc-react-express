package com.example.springjdbc.mapper.product;

import com.example.springjdbc.model.product.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product(
                rs.getString("productCode"),
                rs.getString("productName"),
                rs.getString("productLine"),
                rs.getString("productScale"),
                rs.getString("productVendor"),
                rs.getString("productDescription"),
                rs.getInt("quantityInStock"),
                rs.getBigDecimal("buyPrice"),
                rs.getBigDecimal("MSRP")
        );
    }
}
