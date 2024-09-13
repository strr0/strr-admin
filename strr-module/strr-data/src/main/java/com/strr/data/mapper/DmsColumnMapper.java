package com.strr.data.mapper;

import com.strr.base.mapper.CrudMapper;
import com.strr.data.model.DmsColumn;

import java.util.List;

/**
 * 业务字段信息
 */
public interface DmsColumnMapper extends CrudMapper<DmsColumn, Integer> {
    /**
     * 根据表名获取数据库字段信息
     */
    List<DmsColumn> listDbColumnByTable(String table);

    /**
     * 根据 tableId 删除字段信息
     */
    void removeByTableId(Integer tableId);
}
