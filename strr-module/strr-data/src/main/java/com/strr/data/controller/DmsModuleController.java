package com.strr.data.controller;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.base.model.Result;
import com.strr.constant.Constant;
import com.strr.data.model.DmsModule;
import com.strr.data.model.DmsTable;
import com.strr.data.model.bo.DmsTableBo;
import com.strr.data.model.vo.DmsModuleVo;
import com.strr.data.service.DmsHandleService;
import com.strr.data.service.DmsModuleService;
import org.springframework.web.bind.annotation.*;

/**
 * 模块信息
 */
@RestController
@RequestMapping("/module")
public class DmsModuleController {
    private final DmsModuleService dmsModuleService;
    private final DmsHandleService dmsHandleService;

    public DmsModuleController(DmsModuleService dmsModuleService, DmsHandleService dmsHandleService) {
        this.dmsModuleService = dmsModuleService;
        this.dmsHandleService = dmsHandleService;
    }

    /**
     * 查询模块信息
     */
    @GetMapping("/page")
    public Result<Page<DmsModule>> page(DmsModule param, Pageable pageable) {
        Page<DmsModule> page = dmsModuleService.page(param, pageable);
        return Result.ok(page);
    }

    /**
     * 查询数据库表信息
     */
    @GetMapping("/db/page")
    public Result<Page<DmsTable>> pageDbTable(DmsTableBo param, Pageable pageable) {
        Page<DmsTable> page = dmsModuleService.pageDbTable(param, pageable);
        return Result.ok(page);
    }

    /**
     * 导入表信息
     */
    @PostMapping("/import")
    public Result<Void> importTable(String[] tables) {
        dmsModuleService.importTable(tables);
        return Result.ok();
    }

    /**
     * 更新模块信息
     */
    @PutMapping
    public Result<Void> update(@RequestBody DmsModuleVo moduleVo) {
        dmsModuleService.updateInfo(moduleVo);
        return Result.ok();
    }

    /**
     * 删除模块信息
     */
    @DeleteMapping("/{tableId}")
    public Result<Void> remove(@PathVariable Integer tableId) {
        dmsModuleService.removeInfoByTableId(tableId);
        return Result.ok();
    }

    /**
     * 获取模块信息
     */
    @GetMapping("/{id}")
    public Result<DmsModuleVo> getModuleInfo(@PathVariable Integer id) {
        DmsModuleVo moduleInfo = dmsModuleService.getInfo(id);
        return Result.ok(moduleInfo);
    }

    /**
     * 注册信息
     */
    @PostMapping("/register")
    public Result<Void> register(Integer id) {
        try {
            dmsHandleService.register(id);
            dmsModuleService.updateStatus(id, Constant.YES);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error();
    }
}
