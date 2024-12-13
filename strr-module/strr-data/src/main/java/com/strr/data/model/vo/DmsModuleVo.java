package com.strr.data.model.vo;

import com.strr.data.model.DmsColumn;
import com.strr.data.model.DmsTable;

import java.util.List;

/**
 * 模块信息
 */
public class DmsModuleVo {
    /**
     * 编号
     */
    private Long id;

    /**
     * 归属表编号
     */
    private Long tableId;

    /**
     * 模块名称
     */
    private String name;

    /**
     * 模块编码
     */
    private String code;

    /**
     * 路径
     */
    private String path;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private String status;

    /**
     * 业务表信息
     */
    private DmsTable table;

    /**
     * 业务字段信息
     */
    private List<DmsColumn> columns;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public DmsTable getTable() {
        return table;
    }

    public void setTable(DmsTable table) {
        this.table = table;
    }

    public List<DmsColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<DmsColumn> columns) {
        this.columns = columns;
    }
}
