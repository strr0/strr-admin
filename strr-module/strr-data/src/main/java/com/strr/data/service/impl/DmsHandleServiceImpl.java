package com.strr.data.service.impl;

import com.strr.data.handle.DaoHandler;
import com.strr.data.handle.WebHandler;
import com.strr.data.handle.factory.WebHandlerFactory;
import com.strr.data.mapper.DmsModuleMapper;
import com.strr.data.model.vo.DmsModuleVo;
import com.strr.data.service.IDmsHandleService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 数据处理
 */
@Service
public class DmsHandleServiceImpl implements IDmsHandleService {
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final SqlSessionFactory sqlSessionFactory;
    private final DmsModuleMapper dmsModuleMapper;

    public DmsHandleServiceImpl(RequestMappingHandlerMapping requestMappingHandlerMapping, SqlSessionFactory sqlSessionFactory,
                                DmsModuleMapper dmsModuleMapper) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.sqlSessionFactory = sqlSessionFactory;
        this.dmsModuleMapper = dmsModuleMapper;
    }

    /**
     * 注册
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(Integer id) throws NoSuchMethodException {
        DmsModuleVo module = dmsModuleMapper.getInfo(id);
        // 构建数据库查询
        DaoHandler daoHandler = new DaoHandler.Builder(module).build();
        daoHandler.handle(sqlSessionFactory.getConfiguration());
        // 构建接口
        WebHandler webHandler = WebHandlerFactory.buildWebHandler(sqlSessionFactory, module.getCode());
        WebHandlerFactory.registerMapping(requestMappingHandlerMapping, webHandler, module.getPath());
    }
}
