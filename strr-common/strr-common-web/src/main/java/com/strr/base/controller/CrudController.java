package com.strr.base.controller;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.base.model.Result;
import com.strr.base.service.CrudService;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

public abstract class CrudController<T, ID extends Serializable> {
    protected abstract CrudService<T, ID> getService();

    @GetMapping("/page")
    public Page<T> page(T param, Pageable pageable) {
        return getService().page(param, pageable);
    }

    @PostMapping
    public Result<T> save(@RequestBody T entity) {
        int r = getService().save(entity);
        if (r > 0) {
            return Result.ok(entity);
        }
        return Result.error();
    }

    @PutMapping
    public Result<T> update(@RequestBody T entity) {
        int r = getService().update(entity);
        if (r > 0) {
            return Result.ok(entity);
        }
        return Result.error();
    }

    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable ID id) {
        int r = getService().remove(id);
        if (r > 0) {
            return Result.ok();
        }
        return Result.error();
    }

    @GetMapping("/{id}")
    public Result<T> get(@PathVariable ID id) {
        T t = getService().get(id);
        if (t != null) {
            return Result.ok(t);
        }
        return Result.error();
    }
}
