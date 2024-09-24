package com.strr.base.controller;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.base.model.Result;
import com.strr.base.service.ICrudService;

import java.io.Serializable;

public abstract class CrudController<T, ID extends Serializable> {
    protected abstract ICrudService<T, ID> getService();

    public Result<Page<T>> page(T param, Pageable pageable) {
        Page<T> page = getService().page(param, pageable);
        return Result.ok(page);
    }

    public Result<T> save(T entity) {
        int r = getService().save(entity);
        if (r > 0) {
            return Result.ok(entity);
        }
        return Result.error();
    }

    public Result<T> update(T entity) {
        int r = getService().update(entity);
        if (r > 0) {
            return Result.ok(entity);
        }
        return Result.error();
    }

    public Result<Void> remove(ID id) {
        int r = getService().remove(id);
        if (r > 0) {
            return Result.ok();
        }
        return Result.error();
    }

    public Result<T> get(ID id) {
        T t = getService().get(id);
        if (t != null) {
            return Result.ok(t);
        }
        return Result.error();
    }
}
