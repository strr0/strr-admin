package com.strr.system.model;

import com.strr.base.model.BaseModel;
import com.strr.mybatis.annotation.Column;
import com.strr.mybatis.annotation.Id;
import com.strr.mybatis.annotation.Table;

/**
 * 资源
 */
@Table("sys_resource")
public class SysResource extends BaseModel {
    /**
     * 编号
     */
    @Id
    private Long id;

    /**
     * 名称
     */
    @Column(fuzzy = true)
    private String name;

    /**
     * 类型 (D.目录 M.菜单 B.按钮)
     */
    private String type;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 路由组件
     */
    private String component;

    /**
     * 国际化 key
     */
    private String i18nKey;

    /**
     * 父菜单
     */
    @Column("parent_id")
    private Long parentId;

    /**
     * 是否外链（1.是 0.否）
     */
    private String frame;

    /**
     * 是否缓存（1.缓存 0.不缓存）
     */
    private String cache;

    /**
     * 图标
     */
    private String icon;

    /**
     * 图标类型（1.iconify图标 2.本地图标）
     */
    private String iconType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否展示（1.是 0.否）
     */
    private String visible;

    /**
     * 权限字符串
     */
    private String perms;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getI18nKey() {
        return i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
