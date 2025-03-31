package com.example.springjdbc.service;

import com.example.springjdbc.dto.customer.CustomerDTO;
import com.example.springjdbc.mapper.customer.CustomerMapper;
import com.example.springjdbc.model.customer.Customer;
import com.example.springjdbc.repository.customer.CustomerRepository;
import com.example.springjdbc.service.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerDTO dto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1, "Acme", "Doe", "John", "123456", "Street 1", null, "City", "State", "12345", "Country", null, new BigDecimal("1000.00"));
        dto = new CustomerDTO(1, "Acme", "John Doe", "123456", "Street 1", "City", "State", "Country", new BigDecimal("1000.00"));
    }

    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAllCustomers(0, 10)).thenReturn(List.of(customer));
        when(customerMapper.toDTO(any())).thenReturn(dto);

        List<CustomerDTO> result = customerService.getAllCustomers(0, 10);

        assertEquals(1, result.size());
        assertEquals("Acme", result.get(0).getCustomerName());
    }

    @Test
    void testGetCustomerById() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerMapper.toDTO(customer)).thenReturn(dto);

        CustomerDTO result = customerService.getCustomerById(1);
        assertNotNull(result);
        assertEquals("Acme", result.getCustomerName());
    }

    @Test
    void testCreateCustomer() {
        when(customerMapper.toEntity(dto)).thenReturn(customer);
        customerService.createCustomer(dto);
        verify(customerRepository).saveCustomer(customer);
    }

    @Test
    void testUpdateCustomer() {
        when(customerMapper.toEntity(dto)).thenReturn(customer);
        customerService.updateCustomer(dto);
        verify(customerRepository).updateCustomer(customer);
    }

    @Test
    void testDeleteCustomer() {
        customerService.deleteCustomer(1);
        verify(customerRepository).deleteCustomer(1);
    }
}

