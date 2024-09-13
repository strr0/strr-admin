package com.strr.data.service;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.data.model.DmsTable;
import com.strr.data.model.bo.DmsTableBo;

/**
 * 元数据信息
 */
public interface DmsTableService {
    /**
     * 查询业务表信息
     */
    Page<DmsTable> pageTable(DmsTable param, Pageable pageable);

    /**
     * 查询数据库表信息
     */
    Page<DmsTable> pageDbTable(DmsTableBo param, Pageable pageable);

    /**
     * 导入表信息
     */
    void importTable(String[] tables);

    /**
     * 删除业务表信息
     */
    void removeTableInfo(Integer id);

    /**
     * 更新业务表状态
     */
    void updateTableStatus(Integer id, String status);
}
