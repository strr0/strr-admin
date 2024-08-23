package com.strr.system.model;

import com.strr.base.annotation.Column;
import com.strr.base.annotation.Id;
import com.strr.base.annotation.Table;
import com.strr.base.model.BaseModel;

/**
 * 资源
 */
@Table("sys_resource")
public class SysResource extends BaseModel {
    /**
     * 编号
     */
    @Id
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型 (F.目录 M.菜单 B.按钮)
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
     * 标题
     */
    @Column(fuzzy = true)
    private String title;

    /**
     * 父菜单
     */
    @Column("parent_id")
    private Integer parentId;

    /**
     * 是否为外链（1是 0否）
     */
    private String frame;

    /**
     * 是否缓存（1缓存 0不缓存）
     */
    private String cache;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    @Column("`order`")
    private Integer order;

    /**
     * 隐藏
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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
