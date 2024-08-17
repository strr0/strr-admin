package com.strr.base.mapper;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface CrudMapper<T, ID extends Serializable> {
    int countByParam(T param);

    List<T> listByParam(T param);

    int save(T entity);

    int update(T entity);

    int remove(ID id);

    T get(ID id);

    Page<T> page(@Param("param") T param, @Param("page") Pageable pageable);
}
