package com.strr.admin.controller;

import com.strr.admin.model.SysResource;
import com.strr.admin.model.vo.SysResourceVO;
import com.strr.admin.service.SysResourceService;
import com.strr.admin.util.MenuUtil;
import com.strr.base.controller.SCrudController;
import com.strr.base.model.Result;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class DefaultSysResourceController extends SCrudController<SysResource, Integer> {
    private final SysResourceService sysResourceService;

    public DefaultSysResourceController(SysResourceService sysResourceService) {
        this.sysResourceService = sysResourceService;
    }

    @Override
    protected SysResourceService getService() {
        return sysResourceService;
    }

    /**
     * 菜单树
     */
    @GetMapping("/menuTree")
    public Result<List<SysResourceVO>> menuTree(SysResource param) {
        List<SysResource> sysResourceList = sysResourceService.listByParam(param);
        return Result.ok(MenuUtil.buildMenuTree(sysResourceList));
    }

    /**
     * 删除权限
     */
    @DeleteMapping("/removeInfo")
    public Result<Void> removeInfo(Integer id) {
        sysResourceService.removeInfo(id);
        return Result.ok();
    }
}
