package com.example.springjdbc.service.office;

import com.example.springjdbc.dto.office.OfficeDTO;
import com.example.springjdbc.mapper.office.OfficeMapper;
import com.example.springjdbc.model.office.Office;
import com.example.springjdbc.repository.office.OfficeRepository;
import com.example.springjdbc.util.AbstractCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfficeServiceImpl extends AbstractCrudService<OfficeDTO, Office, String>
        implements OfficeService {

    private static final Logger log = LoggerFactory.getLogger(OfficeServiceImpl.class);

    private final OfficeRepository repository;
    private final OfficeMapper mapper;

    public OfficeServiceImpl(OfficeRepository repository, OfficeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // === Abstract Method Implementations ===

    @Override
    protected List<Office> findAllEntities() {
        log.debug("Fetching all offices");
        return repository.findAll();
    }

    @Override
    protected Optional<Office> findEntityById(String id) {
        log.debug("Finding office by ID: {}", id);
        return repository.findById(id);
    }

    @Override
    protected void saveEntity(Office entity) {
        log.debug("Saving office: {}", entity);
        repository.save(entity);
    }

    @Override
    protected void updateEntity(Office entity) {
        log.debug("Updating office: {}", entity);
        repository.update(entity);
    }

    @Override
    protected void deleteEntityById(String id) {
        log.debug("Deleting office with ID: {}", id);
        repository.delete(id);
    }

    @Override
    protected OfficeDTO toDTO(Office entity) {
        return mapper.toDTO(entity);
    }

    @Override
    protected Office toEntity(OfficeDTO dto) {
        return mapper.toEntity(dto);
    }
}