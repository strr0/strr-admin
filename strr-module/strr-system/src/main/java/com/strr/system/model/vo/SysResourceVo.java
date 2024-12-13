package com.strr.system.model.vo;

import com.strr.base.model.Treeable;
import com.strr.system.model.SysResource;

import java.util.List;

public class SysResourceVo extends SysResource implements Treeable<SysResourceVo, Long> {
    private List<SysResourceVo> children;

    @Override
    public List<SysResourceVo> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<SysResourceVo> children) {
        this.children = children;
    }
}
