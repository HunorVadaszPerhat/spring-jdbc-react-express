package com.example.springjdbc.repository.office;

import com.example.springjdbc.exception.RepositoryException;
import com.example.springjdbc.mapper.office.OfficeRowMapper;
import com.example.springjdbc.model.office.Office;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class OfficeRepository {

    private static final Logger log = LoggerFactory.getLogger(OfficeRepository.class);

    private final DataSource dataSource;
    private final OfficeRowMapper rowMapper;

    public OfficeRepository(DataSource dataSource, OfficeRowMapper rowMapper) {
        this.dataSource = dataSource;
        this.rowMapper = rowMapper;
    }

    public List<Office> findAll() {
        String sql = "SELECT * FROM offices ORDER BY officeCode";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Office> offices = new ArrayList<>();
            while (rs.next()) {
                offices.add(rowMapper.mapRow(rs, rs.getRow()));
            }
            log.info("Fetched {} offices", offices.size());
            return offices;

        } catch (SQLException e) {
            log.error("Failed to fetch offices", e);
            throw new RepositoryException("Failed to fetch offices", e);
        }
    }

    public Optional<Office> findById(String id) {
        String sql = "SELECT * FROM offices WHERE officeCode = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Optional<Office> result = rs.next() ? Optional.of(rowMapper.mapRow(rs, rs.getRow())) : Optional.empty();
                log.info("Fetched office by ID {}: {}", id, result.isPresent());
                return result;
            }

        } catch (SQLException e) {
            log.error("Failed to fetch office by ID: {}", id, e);
            throw new RepositoryException("Failed to fetch office by ID: " + id, e);
        }
    }

    public void save(Office o) {
        String sql = """
            INSERT INTO offices (
                officeCode, city, phone, addressLine1, addressLine2,
                state, country, postalCode, territory
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setOfficeParameters(ps, o, false);
            ps.executeUpdate();
            log.info("Saved office: {}", o.getOfficeCode());

        } catch (SQLException e) {
            log.error("Failed to save office: {}", o.getOfficeCode(), e);
            throw new RepositoryException("Failed to save office", e);
        }
    }

    public void update(Office o) {
        String sql = """
            UPDATE offices SET city=?, phone=?, addressLine1=?, addressLine2=?, state=?,
                country=?, postalCode=?, territory=?
            WHERE officeCode = ?
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setOfficeParameters(ps, o, true);
            ps.executeUpdate();
            log.info("Updated office: {}", o.getOfficeCode());

        } catch (SQLException e) {
            log.error("Failed to update office: {}", o.getOfficeCode(), e);
            throw new RepositoryException("Failed to update office", e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM offices WHERE officeCode = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();
            log.info("Deleted office with ID: {}", id);

        } catch (SQLException e) {
            log.error("Failed to delete office with ID: {}", id, e);
            throw new RepositoryException("Failed to delete office with ID: " + id, e);
        }
    }

    // === 🛠️ Parameter Setter Helper ===
    private void setOfficeParameters(PreparedStatement ps, Office o, boolean isUpdate) throws SQLException {
        if (!isUpdate) {
            ps.setString(1, o.getOfficeCode());
            ps.setString(2, o.getCity());
            ps.setString(3, o.getPhone());
            ps.setString(4, o.getAddressLine1());
            ps.setString(5, o.getAddressLine2());
            ps.setString(6, o.getState());
            ps.setString(7, o.getCountry());
            ps.setString(8, o.getPostalCode());
            ps.setString(9, o.getTerritory());
        } else {
            ps.setString(1, o.getCity());
            ps.setString(2, o.getPhone());
            ps.setString(3, o.getAddressLine1());
            ps.setString(4, o.getAddressLine2());
            ps.setString(5, o.getState());
            ps.setString(6, o.getCountry());
            ps.setString(7, o.getPostalCode());
            ps.setString(8, o.getTerritory());
            ps.setString(9, o.getOfficeCode()); // WHERE clause
        }
    }
}