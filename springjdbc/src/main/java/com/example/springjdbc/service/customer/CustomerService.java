package com.example.springjdbc.service.customer;

import com.example.springjdbc.dto.customer.CustomerDTO;
import com.example.springjdbc.exception.EntityNotFoundException;
import com.example.springjdbc.mapper.customer.CustomerMapper;
import com.example.springjdbc.model.customer.Customer;
import com.example.springjdbc.repository.customer.CustomerRepository;
import com.example.springjdbc.util.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public interface CustomerService extends CrudService<CustomerDTO, Integer> {
    Page<CustomerDTO> getAllCustomers(int page, int size); // Custom feature
}