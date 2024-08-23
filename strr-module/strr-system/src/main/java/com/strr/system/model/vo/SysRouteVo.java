package com.strr.system.model.vo;

import java.util.List;

/**
 * 路由
 */
public class SysRouteVo {
    /**
     * 路由名称
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 路由组件
     */
    private String component;

    /**
     * 子路由
     */
    private List<SysRouteVo> children;

    /**
     * 路由描述
     */
    private SysRouteMetaVo meta;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public List<SysRouteVo> getChildren() {
        return children;
    }

    public void setChildren(List<SysRouteVo> children) {
        this.children = children;
    }

    public SysRouteMetaVo getMeta() {
        return meta;
    }

    public void setMeta(SysRouteMetaVo meta) {
        this.meta = meta;
    }
}
