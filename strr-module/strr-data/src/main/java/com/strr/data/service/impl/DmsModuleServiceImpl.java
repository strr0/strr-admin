package com.strr.data.service.impl;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.constant.Constant;
import com.strr.data.mapper.DmsColumnMapper;
import com.strr.data.mapper.DmsModuleMapper;
import com.strr.data.mapper.DmsTableMapper;
import com.strr.data.model.DmsColumn;
import com.strr.data.model.DmsModule;
import com.strr.data.model.DmsTable;
import com.strr.data.model.bo.DmsTableBo;
import com.strr.data.model.vo.DmsModuleVo;
import com.strr.data.service.IDmsModuleService;
import com.strr.util.ModelUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 模块信息
 */
@Service
public class DmsModuleServiceImpl implements IDmsModuleService {
    private static final List<String> baseColumns = Arrays.asList("create_by", "create_time", "update_by", "update_time");
    private final DmsTableMapper dmsTableMapper;
    private final DmsColumnMapper dmsColumnMapper;
    private final DmsModuleMapper dmsModuleMapper;

    public DmsModuleServiceImpl(DmsTableMapper dmsTableMapper, DmsColumnMapper dmsColumnMapper, DmsModuleMapper dmsModuleMapper) {
        this.dmsTableMapper = dmsTableMapper;
        this.dmsColumnMapper = dmsColumnMapper;
        this.dmsModuleMapper = dmsModuleMapper;
    }

    /**
     * 查询模块信息
     */
    @Override
    public Page<DmsModule> page(DmsModule param, Pageable pageable) {
        return dmsModuleMapper.page(param, pageable);
    }

    /**
     * 查询数据库表信息
     */
    @Override
    public Page<DmsTable> pageDbTable(DmsTableBo param, Pageable pageable) {
        param.setExcludeNames(dmsTableMapper.listName());
        return dmsTableMapper.pageDbTable(param, pageable);
    }

    /**
     * 导入表信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importTable(String[] tables) {
        List<DmsTable> dmsTables = dmsTableMapper.listDbTableByNames(tables);
        for (DmsTable dmsTable : dmsTables) {
            if (dmsTableMapper.save(dmsTable) > 0) {
                // 保存模块信息
                DmsModule module = new DmsModule();
                module.setTableId(dmsTable.getId());
                module.setName(dmsTable.getComment());
                String camelCaseName = ModelUtil.toCamelCase(dmsTable.getName());
                module.setCode(camelCaseName);
                module.setPath(camelCaseName);
                module.setStatus(Constant.YES);
                dmsModuleMapper.save(module);
                // 保存字段信息
                List<DmsColumn> dmsColumns = dmsColumnMapper.listDbColumnByTable(dmsTable.getName());
                for (DmsColumn dmsColumn : dmsColumns) {
                    dmsColumn.setTableId(dmsTable.getId());
                    if (!baseColumns.contains(dmsColumn.getName()) && dmsColumn.getOrder() < 10) {
                        dmsColumn.setForm(Constant.YES);
                        dmsColumn.setVisible(Constant.YES);
                    }
                    dmsColumnMapper.save(dmsColumn);
                }
            }
        }
    }

    /**
     * 更新模块信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(DmsModuleVo moduleVo) {
        DmsModule module = new DmsModule();
        module.setId(moduleVo.getId());
        module.setTableId(moduleVo.getTableId());
        module.setName(moduleVo.getName());
        module.setCode(moduleVo.getCode());
        module.setPath(moduleVo.getPath());
        module.setRemark(moduleVo.getRemark());
        module.setStatus(moduleVo.getStatus());
        dmsModuleMapper.update(module);
        DmsTable table = moduleVo.getTable();
        if (table != null) {
            dmsTableMapper.update(table);
        }
        List<DmsColumn> columns = moduleVo.getColumns();
        if (columns != null && !columns.isEmpty()) {
            columns.forEach(dmsColumnMapper::update);
        }
    }

    /**
     * 删除业务表信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeInfoByTableId(Integer tableId) {
        dmsColumnMapper.removeByTableId(tableId);
        dmsModuleMapper.removeByTableId(tableId);
        dmsTableMapper.remove(tableId);
    }

    /**
     * 获取模块信息
     */
    @Override
    public DmsModuleVo getInfo(Integer id) {
        return dmsModuleMapper.getInfo(id);
    }

    /**
     * 更新业务表状态
     */
    @Override
    public void updateStatus(Integer id, String status) {
        DmsModule module = new DmsModule();
        module.setId(id);
        module.setStatus(status);
        dmsModuleMapper.update(module);
    }
}
