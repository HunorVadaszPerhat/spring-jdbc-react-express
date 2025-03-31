package com.example.springjdbc.repository.productline;

import com.example.springjdbc.exception.RepositoryException;
import com.example.springjdbc.mapper.productline.ProductLineRowMapper;
import com.example.springjdbc.model.productline.ProductLine;
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
public class ProductLineRepository {

    private static final Logger log = LoggerFactory.getLogger(ProductLineRepository.class);

    private final DataSource dataSource;
    private final ProductLineRowMapper rowMapper;

    public ProductLineRepository(DataSource dataSource, ProductLineRowMapper rowMapper) {
        this.dataSource = dataSource;
        this.rowMapper = rowMapper;
    }

    public List<ProductLine> findAll() {
        String sql = "SELECT * FROM productlines";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<ProductLine> lines = new ArrayList<>();
            while (rs.next()) {
                lines.add(rowMapper.mapRow(rs, rs.getRow()));
            }
            log.info("Fetched {} product lines", lines.size());
            return lines;

        } catch (SQLException e) {
            log.error("Failed to fetch product lines", e);
            throw new RepositoryException("Failed to fetch product lines", e);
        }
    }

    public Optional<ProductLine> findById(String id) {
        String sql = "SELECT * FROM productlines WHERE productLine = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Optional<ProductLine> result = rs.next()
                        ? Optional.of(rowMapper.mapRow(rs, rs.getRow()))
                        : Optional.empty();
                log.info("Fetched product line with ID: {}", id);
                return result;
            }

        } catch (SQLException e) {
            log.error("Failed to fetch product line with ID: {}", id, e);
            throw new RepositoryException("Failed to fetch product line", e);
        }
    }

    public void save(ProductLine p) {
        String sql = """
            INSERT INTO productlines (productLine, textDescription, htmlDescription, image)
            VALUES (?, ?, ?, ?)
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setProductLineParameters(ps, p, false);
            ps.executeUpdate();
            log.info("Saved product line with ID: {}", p.getProductLine());

        } catch (SQLException e) {
            log.error("Failed to save product line: {}", p.getProductLine(), e);
            throw new RepositoryException("Failed to save product line", e);
        }
    }

    public void update(ProductLine p) {
        String sql = """
            UPDATE productlines SET textDescription=?, htmlDescription=?, image=?
            WHERE productLine = ?
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setProductLineParameters(ps, p, true);
            ps.executeUpdate();
            log.info("Updated product line with ID: {}", p.getProductLine());

        } catch (SQLException e) {
            log.error("Failed to update product line: {}", p.getProductLine(), e);
            throw new RepositoryException("Failed to update product line", e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM productlines WHERE productLine = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();
            log.info("Deleted product line with ID: {}", id);

        } catch (SQLException e) {
            log.error("Failed to delete product line: {}", id, e);
            throw new RepositoryException("Failed to delete product line", e);
        }
    }

    // === 🛠️ Parameter Setter Helper ===
    private void setProductLineParameters(PreparedStatement ps, ProductLine p, boolean isUpdate) throws SQLException {
        if (!isUpdate) {
            ps.setString(1, p.getProductLine());
            ps.setString(2, p.getTextDescription());
            ps.setString(3, p.getHtmlDescription());
            ps.setBytes(4, p.getImage());
        } else {
            ps.setString(1, p.getTextDescription());
            ps.setString(2, p.getHtmlDescription());
            ps.setBytes(3, p.getImage());
            ps.setString(4, p.getProductLine()); // WHERE clause
        }
    }
}