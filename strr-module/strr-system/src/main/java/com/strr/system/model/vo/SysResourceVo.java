package com.strr.system.model.vo;

import com.strr.system.model.SysResource;

import java.util.List;

public class SysResourceVo extends SysResource {
    private List<SysResourceVo> children;

    public List<SysResourceVo> getChildren() {
        return children;
    }

    public void setChildren(List<SysResourceVo> children) {
        this.children = children;
    }
}
