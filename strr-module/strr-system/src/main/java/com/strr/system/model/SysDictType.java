package com.strr.system.model;

import com.strr.base.annotation.Id;
import com.strr.base.annotation.Table;
import com.strr.base.model.BaseModel;

/**
 * 字典类型
 */
@Table("sys_dict_type")
public class SysDictType extends BaseModel {
    /**
     * 字典主键
     */
    @Id
    private Integer id;

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
