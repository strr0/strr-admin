package com.strr.system.controller;

import com.strr.system.model.SysUser;
import com.strr.system.model.bo.SysUserBo;
import com.strr.system.service.SysUserService;
import com.strr.base.controller.CrudController;
import com.strr.base.model.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class SysUserController extends CrudController<SysUser, Integer> {
    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    protected SysUserService getService() {
        return sysUserService;
    }

    /**
     * 保存用户信息
     */
    @PostMapping("/saveInfo")
    public Result<Void> saveInfo(SysUserBo sysUser) {
        sysUserService.saveInfo(sysUser);
        return Result.ok();
    }

    /**
     * 获取用户角色
     */
    @GetMapping("/listRoleId")
    public Result<List<Integer>> listRoleId(Integer userId) {
        List<Integer> data = sysUserService.listRoleId(userId);
        return Result.ok(data);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/removeInfo")
    public Result<Void> removeInfo(Integer id) {
        sysUserService.removeInfo(id);
        return Result.ok();
    }
}
