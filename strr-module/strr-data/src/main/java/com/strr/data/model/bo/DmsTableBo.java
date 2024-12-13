package com.strr.data.model.bo;

import java.util.List;

/**
 * 业务表信息
 */
public class DmsTableBo {
    /**
     * 编号
     */
    private Long id;

    /**
     * 表名称
     */
    private String name;

    /**
     * 表描述
     */
    private String comment;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private String status;

    /**
     * 排除表名
     */
    private List<String> excludeNames;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public List<String> getExcludeNames() {
        return excludeNames;
    }

    public void setExcludeNames(List<String> excludeNames) {
        this.excludeNames = excludeNames;
    }
}
