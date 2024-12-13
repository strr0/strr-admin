package com.strr.data.mapper;

import com.strr.base.mapper.CrudMapper;
import com.strr.data.model.DmsModule;
import com.strr.data.model.vo.DmsModuleVo;

import java.util.List;

/**
 * 模块信息
 */
public interface DmsModuleMapper extends CrudMapper<DmsModule, Long> {
    /**
     * 获取模块信息
     */
    DmsModuleVo getInfo(Long id);

    /**
     * 根据 tableId 删除字段信息
     */
    void removeByTableId(Long tableId);

    /**
     * 获取所有模块信息
     */
    List<DmsModuleVo> listAll(String status);
}
