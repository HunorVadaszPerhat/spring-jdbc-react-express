package com.example.springjdbc.service.customer;

import com.example.springjdbc.dto.customer.CustomerDTO;
import com.example.springjdbc.exception.EntityNotFoundException;
import com.example.springjdbc.mapper.customer.CustomerMapper;
import com.example.springjdbc.model.customer.Customer;
import com.example.springjdbc.repository.customer.CustomerRepository;
import com.example.springjdbc.util.AbstractCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl extends AbstractCrudService<CustomerDTO, Customer, Integer>
        implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    private final CustomerStatsService statsService;

    public CustomerServiceImpl(CustomerRepository repository,
                               CustomerMapper mapper,
                               CustomerStatsService statsService
                               ) {
        this.repository = repository;
        this.mapper = mapper;
        this.statsService = statsService;
    }

    // === Custom Paginated Method ===
//    @Override
//    @Transactional(readOnly = true)
//    public List<CustomerDTO> getAllCustomers(int page, int size) {
//        int offset = page * size;
//        log.debug("Fetching customers with page={}, size={}, offset={}", page, size, offset);
//        return repository.findAllCustomers(offset, size).stream()
//                .map(mapper::toDTO)
//                .collect(Collectors.toList());
//    }


    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDTO> getAllCustomers(int page, int size) {
        int offset = page * size;
        log.debug("Fetching customers with page={}, size={}, offset={}", page, size, offset);

        List<CustomerDTO> customers = repository.findAllCustomers(offset, size).stream()
                .map(mapper::toDTO)
                .toList();

        // ✅ Call service method that has @Cacheable
        long total = statsService.countAllCustomers();// not repository directly

        return new PageImpl<>(customers, PageRequest.of(page, size), total);
    }


    // === Inherited from Abstract ===
    @Override protected List<Customer> findAllEntities() {
        log.debug("Fetching all customers");
        return repository.findAllCustomers(0, Integer.MAX_VALUE);
    }

    @Override protected Optional<Customer> findEntityById(Integer id) {
        log.debug("Customer found with ID: {}", id);
        return repository.findById(id);
    }

    @Override protected void saveEntity(Customer entity) {
        log.debug("Creating customer: {}", entity);
        repository.saveCustomer(entity);
    }

    @Override protected void updateEntity(Customer entity) {
        log.debug("Updating customer: {}", entity);
        repository.updateCustomer(entity);
    }

    @Override protected void deleteEntityById(Integer id) {
        log.debug("Deleting customer with ID: {}", id);
        repository.deleteCustomer(id);
    }

    @Override protected CustomerDTO toDTO(Customer entity) {
        return mapper.toDTO(entity);
    }

    @Override protected Customer toEntity(CustomerDTO dto) {
        return mapper.toEntity(dto);
    }
}