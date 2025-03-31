package com.example.springjdbc.repository.orderdetail;

import com.example.springjdbc.exception.RepositoryException;
import com.example.springjdbc.mapper.orderdetail.OrderDetailRowMapper;
import com.example.springjdbc.model.orderdetail.OrderDetail;
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
public class OrderDetailRepository {

    private static final Logger log = LoggerFactory.getLogger(OrderDetailRepository.class);

    private final DataSource dataSource;
    private final OrderDetailRowMapper rowMapper;

    public OrderDetailRepository(DataSource dataSource, OrderDetailRowMapper rowMapper) {
        this.dataSource = dataSource;
        this.rowMapper = rowMapper;
    }

    public List<OrderDetail> findAll() {
        String sql = "SELECT * FROM orderdetails";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<OrderDetail> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rowMapper.mapRow(rs, rs.getRow()));
            }
            log.info("Fetched {} order details", list.size());
            return list;

        } catch (SQLException e) {
            log.error("Failed to fetch order details", e);
            throw new RepositoryException("Failed to fetch order details", e);
        }
    }

    public Optional<OrderDetail> findById(int orderNumber, String productCode) {
        String sql = "SELECT * FROM orderdetails WHERE orderNumber = ? AND productCode = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderNumber);
            ps.setString(2, productCode);
            try (ResultSet rs = ps.executeQuery()) {
                Optional<OrderDetail> result = rs.next() ? Optional.of(rowMapper.mapRow(rs, rs.getRow())) : Optional.empty();
                log.info("Fetched order detail for order {}, product {}: {}", orderNumber, productCode, result.isPresent());
                return result;
            }

        } catch (SQLException e) {
            log.error("Failed to fetch order detail for order {}, product {}", orderNumber, productCode, e);
            throw new RepositoryException("Failed to fetch order detail", e);
        }
    }

    public void save(OrderDetail od) {
        String sql = """
            INSERT INTO orderdetails (orderNumber, productCode, quantityOrdered, priceEach, orderLineNumber)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setOrderDetailParameters(ps, od, false);
            ps.executeUpdate();
            log.info("Saved order detail: order {}, product {}", od.getOrderNumber(), od.getProductCode());

        } catch (SQLException e) {
            log.error("Failed to save order detail: order {}, product {}", od.getOrderNumber(), od.getProductCode(), e);
            throw new RepositoryException("Failed to save order detail", e);
        }
    }

    public void update(OrderDetail od) {
        String sql = """
            UPDATE orderdetails SET quantityOrdered=?, priceEach=?, orderLineNumber=?
            WHERE orderNumber = ? AND productCode = ?
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setOrderDetailParameters(ps, od, true);
            ps.executeUpdate();
            log.info("Updated order detail: order {}, product {}", od.getOrderNumber(), od.getProductCode());

        } catch (SQLException e) {
            log.error("Failed to update order detail: order {}, product {}", od.getOrderNumber(), od.getProductCode(), e);
            throw new RepositoryException("Failed to update order detail", e);
        }
    }

    public void delete(int orderNumber, String productCode) {
        String sql = "DELETE FROM orderdetails WHERE orderNumber = ? AND productCode = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderNumber);
            ps.setString(2, productCode);
            ps.executeUpdate();
            log.info("Deleted order detail: order {}, product {}", orderNumber, productCode);

        } catch (SQLException e) {
            log.error("Failed to delete order detail: order {}, product {}", orderNumber, productCode, e);
            throw new RepositoryException("Failed to delete order detail", e);
        }
    }

    // === 🛠️ Parameter Setter Helper ===
    private void setOrderDetailParameters(PreparedStatement ps, OrderDetail od, boolean isUpdate) throws SQLException {
        ps.setInt(1, od.getOrderNumber());
        ps.setString(2, od.getProductCode());
        ps.setInt(3, od.getQuantityOrdered());
        ps.setBigDecimal(4, od.getPriceEach());
        ps.setInt(5, od.getOrderLineNumber());

        if (isUpdate) {
            ps.setInt(6, od.getOrderNumber());   // WHERE clause
            ps.setString(7, od.getProductCode());
        }
    }
}