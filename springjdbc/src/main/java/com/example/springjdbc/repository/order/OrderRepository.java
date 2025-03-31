package com.example.springjdbc.repository.order;

import com.example.springjdbc.exception.RepositoryException;
import com.example.springjdbc.mapper.order.OrderRowMapper;
import com.example.springjdbc.model.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {

    private static final Logger log = LoggerFactory.getLogger(OrderRepository.class);

    private final DataSource dataSource;
    private final OrderRowMapper rowMapper;

    public OrderRepository(DataSource dataSource, OrderRowMapper rowMapper) {
        this.dataSource = dataSource;
        this.rowMapper = rowMapper;
    }

    public List<Order> findAll() {
        String sql = "SELECT * FROM orders ORDER BY orderNumber";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                orders.add(rowMapper.mapRow(rs, rs.getRow()));
            }
            log.info("Fetched {} orders", orders.size());
            return orders;

        } catch (SQLException e) {
            log.error("Failed to fetch orders", e);
            throw new RepositoryException("Failed to fetch orders", e);
        }
    }

    public Optional<Order> findById(int id) {
        String sql = "SELECT * FROM orders WHERE orderNumber = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Optional<Order> result = rs.next() ? Optional.of(rowMapper.mapRow(rs, rs.getRow())) : Optional.empty();
                log.info("Fetched order by ID {}: {}", id, result.isPresent());
                return result;
            }

        } catch (SQLException e) {
            log.error("Failed to fetch order by ID: {}", id, e);
            throw new RepositoryException("Failed to fetch order by ID: " + id, e);
        }
    }

    public void save(Order o) {
        String sql = """
            INSERT INTO orders (
                orderNumber, orderDate, requiredDate, shippedDate,
                status, comments, customerNumber
            ) VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setOrderParameters(ps, o, false);
            ps.executeUpdate();
            log.info("Saved order: {}", o.getOrderNumber());

        } catch (SQLException e) {
            log.error("Failed to save order: {}", o.getOrderNumber(), e);
            throw new RepositoryException("Failed to save order", e);
        }
    }

    public void update(Order o) {
        String sql = """
            UPDATE orders SET
                orderDate=?, requiredDate=?, shippedDate=?,
                status=?, comments=?, customerNumber=?
            WHERE orderNumber = ?
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setOrderParameters(ps, o, true);
            ps.executeUpdate();
            log.info("Updated order: {}", o.getOrderNumber());

        } catch (SQLException e) {
            log.error("Failed to update order: {}", o.getOrderNumber(), e);
            throw new RepositoryException("Failed to update order", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM orders WHERE orderNumber = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            log.info("Deleted order with ID: {}", id);

        } catch (SQLException e) {
            log.error("Failed to delete order with ID: {}", id, e);
            throw new RepositoryException("Failed to delete order with ID: " + id, e);
        }
    }

    // === 🛠️ Helper to Set Parameters ===
    private void setOrderParameters(PreparedStatement ps, Order o, boolean isUpdate) throws SQLException {
        if (!isUpdate) {
            ps.setInt(1, o.getOrderNumber());
            ps.setObject(2, o.getOrderDate());
            ps.setObject(3, o.getRequiredDate());
            ps.setObject(4, o.getShippedDate());
            ps.setString(5, o.getStatus());
            ps.setString(6, o.getComments());
            ps.setInt(7, o.getCustomerNumber());
        } else {
            ps.setObject(1, o.getOrderDate());
            ps.setObject(2, o.getRequiredDate());
            ps.setObject(3, o.getShippedDate());
            ps.setString(4, o.getStatus());
            ps.setString(5, o.getComments());
            ps.setInt(6, o.getCustomerNumber());
            ps.setInt(7, o.getOrderNumber()); // WHERE clause
        }
    }
}
