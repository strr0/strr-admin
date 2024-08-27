package com.strr.system.controller;

import com.strr.base.util.LoginUtil;
import com.strr.system.model.SysResource;
import com.strr.system.model.vo.SysResourceVo;
import com.strr.system.model.vo.SysRouteVo;
import com.strr.system.service.SysResourceService;
import com.strr.system.util.MenuUtil;
import com.strr.base.controller.CrudController;
import com.strr.base.model.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resource")
public class SysResourceController extends CrudController<SysResource, Integer> {
    private final SysResourceService sysResourceService;

    public SysResourceController(SysResourceService sysResourceService) {
        this.sysResourceService = sysResourceService;
    }

    @Override
    protected SysResourceService getService() {
        return sysResourceService;
    }

    /**
     * 菜单树
     */
    @PreAuthorize("@pms.hasPermission('system:resource:list')")
    @GetMapping("/menuTree")
    public Result<List<SysResourceVo>> menuTree(SysResource param) {
        List<SysResource> sysResourceList = sysResourceService.listByParam(param);
        return Result.ok(MenuUtil.buildMenuTree(sysResourceList));
    }

    /**
     * 获取路由
     */
    @GetMapping("/getRoutes")
    public Result<List<SysRouteVo>> getRoutes() {
        Integer userId = LoginUtil.getLoginId();
        if (userId == null) {
            return Result.error();
        }
        return Result.ok(MenuUtil.buildRouteTree(sysResourceService.listByUserId(userId)));
    }

    /**
     * 保存资源
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:resource:save')")
    @PostMapping
    public Result<SysResource> save(@RequestBody SysResource sysResource) {
        return super.save(sysResource);
    }

    /**
     * 修改资源
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:resource:update')")
    @PutMapping
    public Result<SysResource> update(@RequestBody SysResource sysResource) {
        return super.update(sysResource);
    }

    /**
     * 删除资源
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:resource:remove')")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Integer id) {
        sysResourceService.removeInfo(id);
        return Result.ok();
    }

    /**
     * 获取资源详情
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:resource:query')")
    @GetMapping("/{id}")
    public Result<SysResource> get(@PathVariable Integer id) {
        return super.get(id);
    }
}
