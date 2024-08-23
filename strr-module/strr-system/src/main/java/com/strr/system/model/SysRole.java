package com.strr.system.model;

import com.strr.base.annotation.Column;
import com.strr.base.annotation.Id;
import com.strr.base.annotation.Table;
import com.strr.base.model.BaseModel;

/**
 * 角色
 */
@Table("sys_role")
public class SysRole extends BaseModel {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 角色名称
     */
    @Column(fuzzy = true)
    private String name;

    /**
     * 角色编码
     */
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
