package com.strr.system.controller;

import com.strr.base.controller.CrudController;
import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.base.model.Result;
import com.strr.system.model.SysDictData;
import com.strr.system.service.ISysDictDataService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dict/data")
public class SysDictDataController extends CrudController<SysDictData, Integer> {
    private final ISysDictDataService sysDictDataService;

    public SysDictDataController(ISysDictDataService sysDictDataService) {
        this.sysDictDataService = sysDictDataService;
    }

    @Override
    protected ISysDictDataService getService() {
        return sysDictDataService;
    }

    /**
     * 查询字段数据列表
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:dict:list')")
    @GetMapping("/page")
    public Result<Page<SysDictData>> page(SysDictData param, Pageable pageable) {
        return super.page(param, pageable);
    }

    /**
     * 新增字典数据
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:dict:save')")
    @PostMapping
    public Result<SysDictData> save(@RequestBody SysDictData entity) {
        return super.save(entity);
    }

    /**
     * 修改字典数据
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:dict:update')")
    @PutMapping
    public Result<SysDictData> update(@RequestBody SysDictData entity) {
        return super.update(entity);
    }

    /**
     * 删除字典数据
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:dict:remove')")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Integer id) {
        return super.remove(id);
    }

    /**
     * 获取字典数据详情
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:dict:query')")
    @GetMapping("/{id}")
    public Result<SysDictData> get(@PathVariable Integer id) {
        return super.get(id);
    }
}
