package com.strr.system.model.vo;

/**
 * 路由元数据
 */
public class SysRouteMetaVo {
    /**
     * 路由标题
     */
    private String title;

    /**
     * 类型 (F.目录 M.菜单 B.按钮)
     */
    private String type;

    /**
     * 是否单路由
     */
    private Boolean single;

    /**
     * 是否缓存
     */
    private Boolean keepAlive;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer order;

    /**
     * 外链地址
     */
    private String href;

    /**
     * 隐藏
     */
    private Boolean hideInMenu;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSingle() {
        return single;
    }

    public void setSingle(Boolean single) {
        this.single = single;
    }

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Boolean getHideInMenu() {
        return hideInMenu;
    }

    public void setHideInMenu(Boolean hideInMenu) {
        this.hideInMenu = hideInMenu;
    }
}
