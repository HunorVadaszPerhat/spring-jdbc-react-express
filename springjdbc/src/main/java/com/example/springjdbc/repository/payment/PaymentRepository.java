package com.example.springjdbc.repository.payment;

import com.example.springjdbc.exception.RepositoryException;
import com.example.springjdbc.mapper.payment.PaymentRowMapper;
import com.example.springjdbc.model.payment.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class PaymentRepository {

    private static final Logger log = LoggerFactory.getLogger(PaymentRepository.class);

    private final DataSource dataSource;
    private final PaymentRowMapper rowMapper;

    public PaymentRepository(DataSource dataSource, PaymentRowMapper rowMapper) {
        this.dataSource = dataSource;
        this.rowMapper = rowMapper;
    }

    public List<Payment> findAll() {
        String sql = "SELECT * FROM payments";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Payment> payments = new ArrayList<>();
            while (rs.next()) {
                payments.add(rowMapper.mapRow(rs, rs.getRow()));
            }
            log.info("Fetched {} payments", payments.size());
            return payments;

        } catch (SQLException e) {
            log.error("Failed to fetch payments", e);
            throw new RepositoryException("Failed to fetch payments", e);
        }
    }

    public Optional<Payment> findById(int customerNumber, String checkNumber) {
        String sql = "SELECT * FROM payments WHERE customerNumber = ? AND checkNumber = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerNumber);
            ps.setString(2, checkNumber);
            try (ResultSet rs = ps.executeQuery()) {
                Optional<Payment> result = rs.next() ? Optional.of(rowMapper.mapRow(rs, rs.getRow())) : Optional.empty();
                log.info("Fetched payment: customer {}, check {}", customerNumber, checkNumber);
                return result;
            }

        } catch (SQLException e) {
            log.error("Failed to fetch payment: customer {}, check {}", customerNumber, checkNumber, e);
            throw new RepositoryException("Failed to fetch payment", e);
        }
    }

    public void save(Payment p) {
        String sql = """
            INSERT INTO payments (customerNumber, checkNumber, paymentDate, amount)
            VALUES (?, ?, ?, ?)
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setPaymentParameters(ps, p, false);
            ps.executeUpdate();
            log.info("Saved payment: customer {}, check {}", p.getCustomerNumber(), p.getCheckNumber());

        } catch (SQLException e) {
            log.error("Failed to save payment: customer {}, check {}", p.getCustomerNumber(), p.getCheckNumber(), e);
            throw new RepositoryException("Failed to save payment", e);
        }
    }

    public void update(Payment p) {
        String sql = """
            UPDATE payments SET paymentDate=?, amount=?
            WHERE customerNumber = ? AND checkNumber = ?
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setPaymentParameters(ps, p, true);
            ps.executeUpdate();
            log.info("Updated payment: customer {}, check {}", p.getCustomerNumber(), p.getCheckNumber());

        } catch (SQLException e) {
            log.error("Failed to update payment: customer {}, check {}", p.getCustomerNumber(), p.getCheckNumber(), e);
            throw new RepositoryException("Failed to update payment", e);
        }
    }

    public void delete(int customerNumber, String checkNumber) {
        String sql = "DELETE FROM payments WHERE customerNumber = ? AND checkNumber = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerNumber);
            ps.setString(2, checkNumber);
            ps.executeUpdate();
            log.info("Deleted payment: customer {}, check {}", customerNumber, checkNumber);

        } catch (SQLException e) {
            log.error("Failed to delete payment: customer {}, check {}", customerNumber, checkNumber, e);
            throw new RepositoryException("Failed to delete payment", e);
        }
    }

    // === 🛠️ Parameter Setter Helper ===
    private void setPaymentParameters(PreparedStatement ps, Payment p, boolean isUpdate) throws SQLException {
        if (!isUpdate) {
            ps.setInt(1, p.getCustomerNumber());
            ps.setString(2, p.getCheckNumber());
            ps.setDate(3, Date.valueOf(p.getPaymentDate()));
            ps.setBigDecimal(4, p.getAmount());
        } else {
            ps.setDate(1, Date.valueOf(p.getPaymentDate()));
            ps.setBigDecimal(2, p.getAmount());
            ps.setInt(3, p.getCustomerNumber());
            ps.setString(4, p.getCheckNumber());
        }
    }
}
