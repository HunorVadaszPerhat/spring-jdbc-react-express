package com.example.springjdbc.daointerface.customer;

import com.example.springjdbc.model.customer.Customer;

public interface CustomerDAO {
    void insert(Customer customer);
    Customer findByCustomerId(int custId);
}
