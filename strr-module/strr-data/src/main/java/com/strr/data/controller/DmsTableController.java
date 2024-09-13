package com.strr.data.controller;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.base.model.Result;
import com.strr.constant.Constant;
import com.strr.data.model.DmsTable;
import com.strr.data.model.bo.DmsTableBo;
import com.strr.data.service.DmsHandleService;
import com.strr.data.service.DmsTableService;
import org.springframework.web.bind.annotation.*;

/**
 * 业务表信息
 */
@RestController
@RequestMapping("/table")
public class DmsTableController {
    private final DmsTableService dmsTableService;
    private final DmsHandleService dmsHandleService;

    public DmsTableController(DmsTableService dmsTableService, DmsHandleService dmsHandleService) {
        this.dmsTableService = dmsTableService;
        this.dmsHandleService = dmsHandleService;
    }

    /**
     * 查询业务表信息
     */
    @GetMapping("/page")
    public Page<DmsTable> pageTable(DmsTable param, Pageable pageable) {
        return dmsTableService.pageTable(param, pageable);
    }

    /**
     * 查询数据库表信息
     */
    @GetMapping("/db/page")
    public Page<DmsTable> pageDbTable(DmsTableBo param, Pageable pageable) {
        return dmsTableService.pageDbTable(param, pageable);
    }

    /**
     * 导入表信息
     */
    @PostMapping("/import")
    public Result<Void> importTable(String[] tables) {
        dmsTableService.importTable(tables);
        return Result.ok();
    }

    /**
     * 删除业务表信息
     */
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Integer id) {
        dmsTableService.removeTableInfo(id);
        return Result.ok();
    }

    /**
     * 注册信息
     */
    @PostMapping("/register")
    public Result<Void> register(Integer id) {
        try {
            dmsHandleService.register(id);
            dmsTableService.updateTableStatus(id, Constant.YES);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error();
    }
}
