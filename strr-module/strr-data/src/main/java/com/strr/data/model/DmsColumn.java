package com.strr.data.model;

import com.strr.base.annotation.Column;
import com.strr.base.annotation.Id;
import com.strr.base.annotation.Table;
import com.strr.base.model.BaseModel;

/**
 * 业务字段信息
 */
@Table("dms_column")
public class DmsColumn extends BaseModel {
    /**
     * 编号
     */
    @Id
    private Integer id;

    /**
     * 归属表编号
     */
    private Integer tableId;

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
    @Column("`order`")
    private Integer order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
