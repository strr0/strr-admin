package com.strr.config.mybatis;

import org.mybatis.spring.mapper.MapperFactoryBean;

public class CrudMapperFactoryBean<T> extends MapperFactoryBean<T> {
    public CrudMapperFactoryBean() {
        super();
    }

    public CrudMapperFactoryBean(Class<T> mapperInterface) {
        super(mapperInterface);
    }

    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();
        // 添加Crud方法
        CrudMappedStatement crudMappedStatement = new CrudMappedStatement.Builder(super.getSqlSession().getConfiguration(), super.getMapperInterface()).build();
        crudMappedStatement.addCrudStatement();
    }
}
