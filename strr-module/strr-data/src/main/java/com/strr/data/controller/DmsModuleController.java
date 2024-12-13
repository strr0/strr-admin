package com.strr.data.controller;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.base.model.Result;
import com.strr.base.constant.Constant;
import com.strr.data.model.DmsModule;
import com.strr.data.model.DmsTable;
import com.strr.data.model.bo.DmsTableBo;
import com.strr.data.model.vo.DmsModuleVo;
import com.strr.data.service.IDmsHandleService;
import com.strr.data.service.IDmsModuleService;
import com.strr.security.annotation.CheckPermission;
import org.springframework.web.bind.annotation.*;

/**
 * 模块信息
 */
@RestController
@RequestMapping("${module.data:}/module")
public class DmsModuleController {
    private final IDmsModuleService dmsModuleService;
    private final IDmsHandleService dmsHandleService;

    public DmsModuleController(IDmsModuleService dmsModuleService, IDmsHandleService dmsHandleService) {
        this.dmsModuleService = dmsModuleService;
        this.dmsHandleService = dmsHandleService;
    }

    /**
     * 查询模块信息
     */
    @CheckPermission("data:module:list")
    @GetMapping("/page")
    public Result<Page<DmsModule>> page(DmsModule param, Pageable pageable) {
        Page<DmsModule> page = dmsModuleService.page(param, pageable);
        return Result.ok(page);
    }

    /**
     * 查询数据库表信息
     */
    @CheckPermission("data:module:import")
    @GetMapping("/db/page")
    public Result<Page<DmsTable>> pageDbTable(DmsTableBo param, Pageable pageable) {
        Page<DmsTable> page = dmsModuleService.pageDbTable(param, pageable);
        return Result.ok(page);
    }

    /**
     * 导入表信息
     */
    @CheckPermission("data:module:import")
    @PostMapping("/import")
    public Result<Void> importTable(String[] tables) {
        dmsModuleService.importTable(tables);
        return Result.ok();
    }

    /**
     * 更新模块信息
     */
    @CheckPermission("data:module:update")
    @PutMapping
    public Result<Void> update(@RequestBody DmsModuleVo moduleVo) {
        dmsModuleService.updateInfo(moduleVo);
        return Result.ok();
    }

    /**
     * 删除模块信息
     */
    @CheckPermission("data:module:remove")
    @DeleteMapping("/{tableId}")
    public Result<Void> remove(@PathVariable Long tableId) {
        dmsModuleService.removeInfoByTableId(tableId);
        return Result.ok();
    }

    /**
     * 获取模块信息
     */
    @CheckPermission("data:module:query")
    @GetMapping("/{id}")
    public Result<DmsModuleVo> getModuleInfo(@PathVariable Long id) {
        DmsModuleVo moduleInfo = dmsModuleService.getInfo(id);
        return Result.ok(moduleInfo);
    }

    /**
     * 注册信息
     */
    @CheckPermission("data:module:register")
    @PostMapping("/register")
    public Result<Void> register(Long id) {
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
