package com.strr.resource.controller;

import com.strr.base.controller.CrudController;
import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.base.model.Result;
import com.strr.resource.model.SysOssConfig;
import com.strr.resource.service.ISysOssConfigService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oss/config")
public class SysOssConfigController extends CrudController<SysOssConfig, Integer> {
    private final ISysOssConfigService sysOssConfigService;

    public SysOssConfigController(ISysOssConfigService sysOssConfigService) {
        this.sysOssConfigService = sysOssConfigService;
    }

    @Override
    protected ISysOssConfigService getService() {
        return sysOssConfigService;
    }

    /**
     * 查询对象存储配置列表
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:ossConfig:list')")
    @GetMapping("/page")
    public Result<Page<SysOssConfig>> page(SysOssConfig param, Pageable pageable) {
        return super.page(param, pageable);
    }

    /**
     * 新增对象存储配置
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:ossConfig:save')")
    @PostMapping
    public Result<SysOssConfig> save(@RequestBody SysOssConfig entity) {
        return super.save(entity);
    }

    /**
     * 修改对象存储配置
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:ossConfig:update')")
    @PutMapping
    public Result<SysOssConfig> update(@RequestBody SysOssConfig entity) {
        return super.update(entity);
    }

    /**
     * 删除对象存储配置
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:ossConfig:remove')")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Integer id) {
        return super.remove(id);
    }

    /**
     * 获取对象存储配置详情
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:ossConfig:query')")
    @GetMapping("/{id}")
    public Result<SysOssConfig> get(@PathVariable Integer id) {
        return super.get(id);
    }
}
