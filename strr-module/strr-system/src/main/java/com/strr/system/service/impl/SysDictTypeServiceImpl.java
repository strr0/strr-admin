package com.strr.system.service.impl;

import com.strr.base.service.impl.CrudServiceImpl;
import com.strr.system.mapper.SysDictTypeMapper;
import com.strr.system.model.SysDictType;
import com.strr.system.service.ISysDictTypeService;
import org.springframework.stereotype.Service;

@Service
public class SysDictTypeServiceImpl extends CrudServiceImpl<SysDictType, Integer> implements ISysDictTypeService {
    private final SysDictTypeMapper sysDictTypeMapper;

    public SysDictTypeServiceImpl(SysDictTypeMapper sysDictTypeMapper) {
        this.sysDictTypeMapper = sysDictTypeMapper;
    }

    @Override
    protected SysDictTypeMapper getMapper() {
        return sysDictTypeMapper;
    }
}
