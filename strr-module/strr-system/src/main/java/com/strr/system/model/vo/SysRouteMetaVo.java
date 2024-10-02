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
     * 国际化 key
     */
    private String i18nKey;

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

    /**
     * 激活菜单
     */
    private String activeMenu;

    /**
     * 权限
     */
    private String perms;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
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

    public String getActiveMenu() {
        return activeMenu;
    }

    public void setActiveMenu(String activeMenu) {
        this.activeMenu = activeMenu;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }
}
