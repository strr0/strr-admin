package com.strr.system.service.impl;

import com.strr.base.service.impl.CrudServiceImpl;
import com.strr.system.mapper.SysDictDataMapper;
import com.strr.system.model.SysDictData;
import com.strr.system.service.ISysDictDataService;
import org.springframework.stereotype.Service;

@Service
public class SysDictDataServiceImpl extends CrudServiceImpl<SysDictData, Long> implements ISysDictDataService {
    private final SysDictDataMapper sysDictDataMapper;

    public SysDictDataServiceImpl(SysDictDataMapper sysDictDataMapper) {
        this.sysDictDataMapper = sysDictDataMapper;
    }

    @Override
    protected SysDictDataMapper getMapper() {
        return sysDictDataMapper;
    }
}
