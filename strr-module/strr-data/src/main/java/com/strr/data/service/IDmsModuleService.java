package com.strr.data.service;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.data.model.DmsModule;
import com.strr.data.model.DmsTable;
import com.strr.data.model.bo.DmsTableBo;
import com.strr.data.model.vo.DmsModuleVo;

/**
 * 模块信息
 */
public interface IDmsModuleService {
    /**
     * 查询模块信息
     */
    Page<DmsModule> page(DmsModule param, Pageable pageable);

    /**
     * 查询数据库表信息
     */
    Page<DmsTable> pageDbTable(DmsTableBo param, Pageable pageable);

    /**
     * 导入表信息
     */
    void importTable(String[] tables);

    /**
     * 更新模块信息
     */
    void updateInfo(DmsModuleVo moduleVo);

    /**
     * 删除模块信息
     */
    void removeInfoByTableId(Integer tableId);

    /**
     * 获取模块信息
     */
    DmsModuleVo getInfo(Integer id);

    /**
     * 更新业务表状态
     */
    void updateStatus(Integer id, String status);
}
