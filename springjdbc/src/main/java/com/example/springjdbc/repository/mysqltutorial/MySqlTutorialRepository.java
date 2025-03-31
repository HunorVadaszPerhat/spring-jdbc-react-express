package com.example.springjdbc.repository.mysqltutorial;

import com.example.springjdbc.exception.DatabaseOperationException;
import com.example.springjdbc.model.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MySqlTutorialRepository {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

//    public List<Customer> getDistinct1() {
//        String sql = "SELECT DISTINCT FROM customers WHERE state IS NOT NULL ORDREE BY state, city";
//
//        List<Customer> customers = new ArrayList<>();  // Initialize an empty list to store Customer objects
//
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement ps = conn.prepareStatement(sql);
//                ResultSet rs = ps.executeQuery()) {  // Execute the query and obtain the result set
//
//            while (rs.next()) {  // Process each row in the result set
//                // Create a new Customer instance for each row
//                Customer customer = new Customer(
//                        rs.getInt("customerNumber"),
//                        rs.getString("customerName"),
//                        rs.getString("contactLastName"),
//                        rs.getString("contactFirstName"),
//                        rs.getString("phone"),
//                        rs.getString("addressLine1"),
//                        rs.getString("addressLine2"),
//                        rs.getString("city"),
//                        rs.getString("state"),
//                        rs.getString("postalCode"),
//                        rs.getString("country"),
//                        rs.getInt("creditLimit")
//                );
//
//                // Add the populated Customer object to the list
//                customers.add(customer);
//            }
//
//            return customers;  // Return the list of Customer objects
//        } catch (SQLException e) {
//            throw new DatabaseOperationException("Error executing database operation", e);
//        }
//    }

}
