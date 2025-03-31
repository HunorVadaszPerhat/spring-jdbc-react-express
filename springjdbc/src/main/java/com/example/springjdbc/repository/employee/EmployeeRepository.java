package com.example.springjdbc.repository.employee;

import com.example.springjdbc.exception.RepositoryException;
import com.example.springjdbc.mapper.employee.EmployeeRowMapper;
import com.example.springjdbc.model.employee.Employee;
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
public class EmployeeRepository {

    private static final Logger log = LoggerFactory.getLogger(EmployeeRepository.class);

    private final DataSource dataSource;
    private final EmployeeRowMapper rowMapper;

    public EmployeeRepository(DataSource dataSource, EmployeeRowMapper rowMapper) {
        this.dataSource = dataSource;
        this.rowMapper = rowMapper;
    }

    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees ORDER BY employeeNumber";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Employee> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rowMapper.mapRow(rs, rs.getRow()));
            }
            log.info("Fetched {} employees", list.size());
            return list;

        } catch (SQLException e) {
            log.error("Failed to fetch employees", e);
            throw new RepositoryException("Failed to fetch employees", e);
        }
    }

    public Optional<Employee> findById(int id) {
        String sql = "SELECT * FROM employees WHERE employeeNumber = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Optional<Employee> result = rs.next() ? Optional.of(rowMapper.mapRow(rs, rs.getRow())) : Optional.empty();
                log.info("Fetched employee by ID {}: {}", id, result.isPresent());
                return result;
            }

        } catch (SQLException e) {
            log.error("Failed to fetch employee by ID: {}", id, e);
            throw new RepositoryException("Failed to fetch employee by ID: " + id, e);
        }
    }

    public void save(Employee e) {
        String sql = """
            INSERT INTO employees (
                employeeNumber, lastName, firstName, extension, email,
                officeCode, reportsTo, jobTitle
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setEmployeeParameters(ps, e, false);
            ps.executeUpdate();
            log.info("Saved employee: {}", e.getEmployeeNumber());

        } catch (SQLException ex) {
            log.error("Failed to save employee: {}", e.getEmployeeNumber(), ex);
            throw new RepositoryException("Failed to save employee", ex);
        }
    }

    public void update(Employee e) {
        String sql = """
            UPDATE employees SET lastName=?, firstName=?, extension=?, email=?,
                officeCode=?, reportsTo=?, jobTitle=?
            WHERE employeeNumber = ?
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setEmployeeParameters(ps, e, true);
            ps.executeUpdate();
            log.info("Updated employee: {}", e.getEmployeeNumber());

        } catch (SQLException ex) {
            log.error("Failed to update employee: {}", e.getEmployeeNumber(), ex);
            throw new RepositoryException("Failed to update employee", ex);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM employees WHERE employeeNumber = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            log.info("Deleted employee with ID: {}", id);

        } catch (SQLException e) {
            log.error("Failed to delete employee with ID: {}", id, e);
            throw new RepositoryException("Failed to delete employee with ID: " + id, e);
        }
    }

    // === 🛠️ Helper method to extract parameter setting ===
    private void setEmployeeParameters(PreparedStatement ps, Employee e, boolean isUpdate) throws SQLException {
        if (!isUpdate) {
            ps.setInt(1, e.getEmployeeNumber());
            ps.setString(2, e.getLastName());
            ps.setString(3, e.getFirstName());
            ps.setString(4, e.getExtension());
            ps.setString(5, e.getEmail());
            ps.setString(6, e.getOfficeCode());
            ps.setObject(7, e.getReportsTo());
            ps.setString(8, e.getJobTitle());
        } else {
            ps.setString(1, e.getLastName());
            ps.setString(2, e.getFirstName());
            ps.setString(3, e.getExtension());
            ps.setString(4, e.getEmail());
            ps.setString(5, e.getOfficeCode());
            ps.setObject(6, e.getReportsTo());
            ps.setString(7, e.getJobTitle());
            ps.setInt(8, e.getEmployeeNumber()); // WHERE clause
        }
    }
}