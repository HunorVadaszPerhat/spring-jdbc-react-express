package com.example.springjdbc.util;

import com.example.springjdbc.exception.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractCrudService<T, E, ID> implements CrudService<T, ID> {

    protected abstract List<E> findAllEntities();
    protected abstract Optional<E> findEntityById(ID id);
    protected abstract void saveEntity(E entity);
    protected abstract void updateEntity(E entity);
    protected abstract void deleteEntityById(ID id);
    protected abstract T toDTO(E entity);
    protected abstract E toEntity(T dto);

    @Override
    @Transactional
    public void create(T dto) {
        saveEntity(toEntity(dto));
    }

    @Override
    @Transactional
    public void update(T dto) {
        updateEntity(toEntity(dto));
    }

    @Override
    @Transactional
    public void delete(ID id) {
        deleteEntityById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return findAllEntities().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public T getById(ID id) {
        return findEntityById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with ID: " + id));
    }
}
