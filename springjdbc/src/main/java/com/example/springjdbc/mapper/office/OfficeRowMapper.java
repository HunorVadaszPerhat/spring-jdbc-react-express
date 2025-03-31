package com.example.springjdbc.mapper.office;

import com.example.springjdbc.model.office.Office;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OfficeRowMapper implements RowMapper<Office> {
    @Override
    public Office mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Office(
                rs.getString("officeCode"),
                rs.getString("city"),
                rs.getString("phone"),
                rs.getString("addressLine1"),
                rs.getString("addressLine2"),
                rs.getString("state"),
                rs.getString("country"),
                rs.getString("postalCode"),
                rs.getString("territory")
        );
    }
}