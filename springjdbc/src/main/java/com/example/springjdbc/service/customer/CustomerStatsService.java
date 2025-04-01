package com.example.springjdbc.service.customer;

import com.example.springjdbc.repository.customer.CustomerRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerStatsService {

    private final CustomerRepository repository;

    public CustomerStatsService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Cacheable("totalCustomers")
    @Transactional(readOnly = true)
    public long countAllCustomers() {
        return repository.countAllCustomers();
    }
}