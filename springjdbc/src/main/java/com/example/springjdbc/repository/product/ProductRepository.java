package com.example.springjdbc.repository.product;

import com.example.springjdbc.exception.RepositoryException;
import com.example.springjdbc.mapper.product.ProductRowMapper;
import com.example.springjdbc.model.product.Product;
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
public class ProductRepository {

    private static final Logger log = LoggerFactory.getLogger(ProductRepository.class);

    private final DataSource dataSource;
    private final ProductRowMapper rowMapper;

    public ProductRepository(DataSource dataSource, ProductRowMapper rowMapper) {
        this.dataSource = dataSource;
        this.rowMapper = rowMapper;
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                products.add(rowMapper.mapRow(rs, rs.getRow()));
            }
            log.info("Fetched {} products", products.size());
            return products;

        } catch (SQLException e) {
            log.error("Failed to fetch products", e);
            throw new RepositoryException("Failed to fetch products", e);
        }
    }

    public Optional<Product> findById(String id) {
        String sql = "SELECT * FROM products WHERE productCode = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Optional<Product> result = rs.next()
                        ? Optional.of(rowMapper.mapRow(rs, rs.getRow()))
                        : Optional.empty();
                log.info("Fetched product with ID: {}", id);
                return result;
            }

        } catch (SQLException e) {
            log.error("Failed to fetch product with ID: {}", id, e);
            throw new RepositoryException("Failed to fetch product", e);
        }
    }

    public void save(Product p) {
        String sql = """
            INSERT INTO products (
                productCode, productName, productLine, productScale, productVendor,
                productDescription, quantityInStock, buyPrice, MSRP
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setProductParameters(ps, p, false);
            ps.executeUpdate();
            log.info("Saved product with ID: {}", p.getProductCode());

        } catch (SQLException e) {
            log.error("Failed to save product with ID: {}", p.getProductCode(), e);
            throw new RepositoryException("Failed to save product", e);
        }
    }

    public void update(Product p) {
        String sql = """
            UPDATE products SET productName=?, productLine=?, productScale=?, productVendor=?,
                productDescription=?, quantityInStock=?, buyPrice=?, MSRP=?
            WHERE productCode = ?
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setProductParameters(ps, p, true);
            ps.executeUpdate();
            log.info("Updated product with ID: {}", p.getProductCode());

        } catch (SQLException e) {
            log.error("Failed to update product with ID: {}", p.getProductCode(), e);
            throw new RepositoryException("Failed to update product", e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM products WHERE productCode = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();
            log.info("Deleted product with ID: {}", id);

        } catch (SQLException e) {
            log.error("Failed to delete product with ID: {}", id, e);
            throw new RepositoryException("Failed to delete product", e);
        }
    }

    // === 🛠️ Parameter Setter Helper ===
    private void setProductParameters(PreparedStatement ps, Product p, boolean isUpdate) throws SQLException {
        ps.setString(1, p.getProductCode());
        ps.setString(2, p.getProductName());
        ps.setString(3, p.getProductLine());
        ps.setString(4, p.getProductScale());
        ps.setString(5, p.getProductVendor());
        ps.setString(6, p.getProductDescription());
        ps.setInt(7, p.getQuantityInStock());
        ps.setBigDecimal(8, p.getBuyPrice());
        ps.setBigDecimal(9, p.getMsrp());

        if (isUpdate) {
            ps.setString(10, p.getProductCode()); // WHERE clause
        }
    }
}