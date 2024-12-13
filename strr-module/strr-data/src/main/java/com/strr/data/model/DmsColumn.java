package com.strr.data.model;

import com.strr.base.model.BaseModel;
import com.strr.mybatis.annotation.Id;
import com.strr.mybatis.annotation.Table;

/**
 * 业务字段信息
 */
@Table("dms_column")
public class DmsColumn extends BaseModel {
    /**
     * 编号
     */
    @Id
    private Long id;

    /**
     * 归属表编号
     */
    private Long tableId;

    /**
     * 列名称
     */
    private String name;

    /**
     * 列描述
     */
    private String comment;

    /**
     * 是否主键
     */
    private String pk;

    /**
     * 是否编辑字段
     */
    private String form;

    /**
     * 是否列表字段
     */
    private String visible;

    /**
     * 排序
     */
    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
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

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
