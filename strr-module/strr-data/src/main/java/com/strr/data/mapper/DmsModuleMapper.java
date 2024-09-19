package com.strr.data.mapper;

import com.strr.base.mapper.CrudMapper;
import com.strr.data.model.DmsModule;
import com.strr.data.model.vo.DmsModuleVo;

/**
 * 模块信息
 */
public interface DmsModuleMapper extends CrudMapper<DmsModule, Integer> {
    /**
     * 获取模块信息
     */
    DmsModuleVo getInfo(Integer id);

    /**
     * 根据 tableId 删除字段信息
     */
    void removeByTableId(Integer tableId);
}
