package com.example.springjdbc.mapper.customer;

import com.example.springjdbc.dto.customer.CustomerDTO;
import com.example.springjdbc.model.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO(
                customer.getCustomerNumber(),
                customer.getCustomerName(),
                customer.getContactFirstName() + " " + customer.getContactLastName(),
                customer.getPhone(),
                customer.getAddressLine1() +
                        (customer.getAddressLine2() != null ? ", " + customer.getAddressLine2() : ""),
                customer.getCity(),
                customer.getState(),
                customer.getCountry(),
                customer.getCreditLimit()
        );
    }

    public Customer toEntity(CustomerDTO dto) {
        String[] names = dto.getContactName().split(" ", 2);
        return new Customer(
                dto.getCustomerNumber(),
                dto.getCustomerName(),
                names.length > 1 ? names[1] : "",
                names.length > 0 ? names[0] : "",
                dto.getPhone(),
                dto.getAddress().split(",")[0],
                dto.getAddress().contains(",") ? dto.getAddress().split(",")[1].trim() : null,
                dto.getCity(),
                dto.getState(),
                null,
                dto.getCountry(),
                null,
                dto.getCreditLimit()
        );
    }
}
