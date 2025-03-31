package com.example.springjdbc.util;

import java.util.List;

public interface CrudService<T, ID> {
    List<T> getAll();
    T getById(ID id);
    void create(T dto);
    void update(T dto);
    void delete(ID id);
}
