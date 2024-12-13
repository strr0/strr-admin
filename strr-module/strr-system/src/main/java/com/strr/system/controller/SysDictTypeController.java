package com.strr.system.controller;

import com.strr.base.controller.CrudController;
import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.base.model.Result;
import com.strr.security.annotation.CheckPermission;
import com.strr.system.model.SysDictType;
import com.strr.system.service.ISysDictTypeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${module.system:}/dict/type")
public class SysDictTypeController extends CrudController<SysDictType, Integer> {
    private final ISysDictTypeService sysDictTypeService;

    public SysDictTypeController(ISysDictTypeService sysDictTypeService) {
        this.sysDictTypeService = sysDictTypeService;
    }

    @Override
    protected ISysDictTypeService getService() {
        return sysDictTypeService;
    }

    /**
     * 查询字典类型列表
     */
    @Override
    @CheckPermission("system:dict:list")
    @GetMapping("/page")
    public Result<Page<SysDictType>> page(SysDictType param, Pageable pageable) {
        return super.page(param, pageable);
    }

    /**
     * 新增字典类型
     */
    @Override
    @CheckPermission("system:dict:save")
    @PostMapping
    public Result<SysDictType> save(@RequestBody SysDictType entity) {
        return super.save(entity);
    }

    /**
     * 修改字典类型
     */
    @Override
    @CheckPermission("system:dict:update")
    @PutMapping
    public Result<SysDictType> update(@RequestBody SysDictType entity) {
        return super.update(entity);
    }

    /**
     * 删除字典类型
     */
    @Override
    @CheckPermission("system:dict:remove")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Integer id) {
        return super.remove(id);
    }

    /**
     * 获取字典类型详情
     */
    @Override
    @CheckPermission("system:dict:query")
    @GetMapping("/{id}")
    public Result<SysDictType> get(@PathVariable Integer id) {
        return super.get(id);
    }
}
