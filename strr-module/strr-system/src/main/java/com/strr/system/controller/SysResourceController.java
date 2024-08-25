package com.strr.system.controller;

import com.strr.system.model.SysResource;
import com.strr.system.model.vo.SysResourceVo;
import com.strr.system.service.SysResourceService;
import com.strr.system.util.MenuUtil;
import com.strr.base.controller.CrudController;
import com.strr.base.model.Result;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/menuTree")
    public Result<List<SysResourceVo>> menuTree(SysResource param) {
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
