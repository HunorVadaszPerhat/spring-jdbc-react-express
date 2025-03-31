package com.example.springjdbc.repository.customer;

import com.example.springjdbc.exception.RepositoryException;
import com.example.springjdbc.mapper.customer.CustomerRowMapper;
import com.example.springjdbc.model.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class CustomerRepository {

    private static final Logger log = LoggerFactory.getLogger(CustomerRepository.class);

    private final DataSource dataSource;
    private final CustomerRowMapper rowMapper;

    public CustomerRepository(DataSource dataSource, CustomerRowMapper rowMapper) {
        this.dataSource = dataSource;
        this.rowMapper = rowMapper;
    }

    public List<Customer> findAllCustomers(int offset, int limit) {
        String sql = "SELECT * FROM customers ORDER BY customerNumber LIMIT ? OFFSET ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                List<Customer> customers = new ArrayList<>();
                while (rs.next()) {
                    customers.add(rowMapper.mapRow(rs, rs.getRow()));
                }
                return customers;
            }
        } catch (SQLException e) {
            log.error("Failed to fetch customers", e);
            throw new RepositoryException("Failed to fetch customers", e);
        }
    }

    public Optional<Customer> findById(int id) {
        String sql = "SELECT * FROM customers WHERE customerNumber = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(rowMapper.mapRow(rs, rs.getRow())) : Optional.empty();
            }
        } catch (SQLException e) {
            log.error("Failed to fetch customer by ID: {}", id, e);
            throw new RepositoryException("Failed to fetch customer by ID: " + id, e);
        }
    }

    public void saveCustomer(Customer c) {
        String sql = """
            INSERT INTO customers (
                customerNumber, customerName, contactLastName, contactFirstName, phone,
                addressLine1, addressLine2, city, state, postalCode, country,
                salesRepEmployeeNumber, creditLimit
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setCustomerParameters(ps, c, false);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error("Failed to save customer: {}", c.getCustomerNumber(), e);
            throw new RepositoryException("Failed to save customer", e);
        }
    }

    public void updateCustomer(Customer c) {
        String sql = """
            UPDATE customers SET customerName=?, contactLastName=?, contactFirstName=?, phone=?,
                addressLine1=?, addressLine2=?, city=?, state=?, postalCode=?, country=?,
                salesRepEmployeeNumber=?, creditLimit=?
            WHERE customerNumber=?
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setCustomerParameters(ps, c, true);
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error("Failed to update customer: {}", c.getCustomerNumber(), e);
            throw new RepositoryException("Failed to update customer", e);
        }
    }

    public void deleteCustomer(int id) {
        String sql = "DELETE FROM customers WHERE customerNumber = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to delete customer with ID: {}", id, e);
            throw new RepositoryException("Failed to delete customer with ID: " + id, e);
        }
    }

    // === 🛠️ Extracted helper method ===
    // === 🛠️ Extracted helper method ===
    private void setCustomerParameters(PreparedStatement ps, Customer c, boolean isUpdate) throws SQLException {
        if (isUpdate) {
            ps.setString(1, c.getCustomerName());
            ps.setString(2, c.getContactLastName());
            ps.setString(3, c.getContactFirstName());
            ps.setString(4, c.getPhone());
            ps.setString(5, c.getAddressLine1());
            ps.setString(6, c.getAddressLine2());
            ps.setString(7, c.getCity());
            ps.setString(8, c.getState());
            ps.setString(9, c.getPostalCode());
            ps.setString(10, c.getCountry());
            ps.setObject(11, c.getSalesRepEmployeeNumber());
            ps.setBigDecimal(12, c.getCreditLimit());
            ps.setInt(13, c.getCustomerNumber()); // WHERE clause
        } else {
            ps.setInt(1, c.getCustomerNumber());
            ps.setString(2, c.getCustomerName());
            ps.setString(3, c.getContactLastName());
            ps.setString(4, c.getContactFirstName());
            ps.setString(5, c.getPhone());
            ps.setString(6, c.getAddressLine1());
            ps.setString(7, c.getAddressLine2());
            ps.setString(8, c.getCity());
            ps.setString(9, c.getState());
            ps.setString(10, c.getPostalCode());
            ps.setString(11, c.getCountry());
            ps.setObject(12, c.getSalesRepEmployeeNumber());
            ps.setBigDecimal(13, c.getCreditLimit());
        }
    }
}



