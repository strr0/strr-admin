package com.strr.system.controller;

import com.strr.base.controller.CrudController;
import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.base.model.Result;
import com.strr.security.annotation.CheckPermission;
import com.strr.system.model.SysOauthClient;
import com.strr.system.service.ISysOauthClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${module.system:}/oauth/client")
public class SysOauthClientController extends CrudController<SysOauthClient, Integer> {
    private final ISysOauthClientService sysOauthClientService;

    public SysOauthClientController(ISysOauthClientService sysOauthClientService) {
        this.sysOauthClientService = sysOauthClientService;
    }

    @Override
    protected ISysOauthClientService getService() {
        return sysOauthClientService;
    }

    /**
     * 查询客户端列表
     */
    @Override
    @CheckPermission("system:client:list")
    @GetMapping("/page")
    public Result<Page<SysOauthClient>> page(SysOauthClient param, Pageable pageable) {
        return super.page(param, pageable);
    }

    /**
     * 新增客户端
     */
    @Override
    @CheckPermission("system:client:save")
    @PostMapping
    public Result<SysOauthClient> save(@RequestBody SysOauthClient entity) {
        return super.save(entity);
    }

    /**
     * 修改客户端
     */
    @Override
    @CheckPermission("system:client:update")
    @PutMapping
    public Result<SysOauthClient> update(@RequestBody SysOauthClient entity) {
        return super.update(entity);
    }

    /**
     * 删除客户端
     */
    @Override
    @CheckPermission("system:client:remove")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Integer id) {
        return super.remove(id);
    }

    /**
     * 获取客户端详情
     */
    @Override
    @CheckPermission("system:client:query")
    @GetMapping("/{id}")
    public Result<SysOauthClient> get(@PathVariable Integer id) {
        return super.get(id);
    }
}
