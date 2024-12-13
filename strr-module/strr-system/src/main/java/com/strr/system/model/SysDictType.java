package com.strr.system.model;

import com.strr.base.model.BaseModel;
import com.strr.mybatis.annotation.Id;
import com.strr.mybatis.annotation.Table;

/**
 * 字典类型
 */
@Table("sys_dict_type")
public class SysDictType extends BaseModel {
    /**
     * 字典主键
     */
    @Id
    private Long id;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典类型
     */
    private String type;

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
