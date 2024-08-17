package com.strr.base.service;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;

import java.io.Serializable;

public interface CrudService<T, ID extends Serializable> {
    Page<T> page(T param, Pageable pageable);

    int save(T entity);

    int update(T entity);

    int remove(ID id);

    T get(ID id);
}
