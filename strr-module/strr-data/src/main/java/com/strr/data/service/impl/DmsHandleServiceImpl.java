package com.strr.data.service.impl;

import com.strr.base.constant.Constant;
import com.strr.data.handle.DaoHandler;
import com.strr.data.handle.WebHandler;
import com.strr.data.handle.factory.WebHandlerFactory;
import com.strr.data.mapper.DmsModuleMapper;
import com.strr.data.model.vo.DmsModuleVo;
import com.strr.data.service.IDmsHandleService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

/**
 * 数据处理
 */
@Service
public class DmsHandleServiceImpl implements IDmsHandleService {
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final SqlSessionFactory sqlSessionFactory;
    private final DmsModuleMapper dmsModuleMapper;
    private final String basePath;

    public DmsHandleServiceImpl(RequestMappingHandlerMapping requestMappingHandlerMapping, SqlSessionFactory sqlSessionFactory,
                                DmsModuleMapper dmsModuleMapper, Environment environment) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.sqlSessionFactory = sqlSessionFactory;
        this.dmsModuleMapper = dmsModuleMapper;
        this.basePath = environment.getProperty("module.data", "");
    }

    /**
     * 注册
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(Long id) throws NoSuchMethodException {
        register(dmsModuleMapper.getInfo(id));
    }

    /**
     * 初始化
     */
    @Override
    public void init() throws NoSuchMethodException {
        List<DmsModuleVo> modules = dmsModuleMapper.listAll(Constant.YES);
        for (DmsModuleVo module : modules) {
            register(module);
        }
    }

    /**
     * 注册
     */
    private void register(DmsModuleVo module) throws NoSuchMethodException {
        // 构建数据库查询
        DaoHandler daoHandler = new DaoHandler.Builder(module).build();
        daoHandler.handle(sqlSessionFactory.getConfiguration());
        // 构建接口
        WebHandler webHandler = WebHandlerFactory.buildWebHandler(sqlSessionFactory, module.getCode());
        String path;
        if (module.getPath().startsWith("/")) {
            path = basePath + module.getPath();
        } else {
            path = basePath + "/" + module.getPath();
        }
        WebHandlerFactory.registerMapping(requestMappingHandlerMapping, webHandler, path);
    }
}
